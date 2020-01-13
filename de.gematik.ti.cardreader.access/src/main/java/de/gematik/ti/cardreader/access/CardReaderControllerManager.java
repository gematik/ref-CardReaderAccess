/*
 * Copyright (c) 2020 gematik GmbH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package de.gematik.ti.cardreader.access;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

import de.gematik.ti.cardreader.provider.api.ICardReader;
import de.gematik.ti.cardreader.provider.api.ICardReaderController;
import de.gematik.ti.cardreader.provider.api.entities.IProviderDescriptor;
import de.gematik.ti.cardreader.provider.spi.ICardReaderControllerProvider;
import de.gematik.ti.utils.streams.stat.StreamUtils;

/**
 * include::{userguide}/CRA_Overview.adoc[tag=CardReaderControllerManager]
 *
 */
public final class CardReaderControllerManager {

    private static CardReaderControllerManager instance;

    private CardReaderControllerManager() {
    }

    /**
     * Get the singleton instance
     * @return singleton instance of CardReaderControllerManager
     */
    public static synchronized CardReaderControllerManager getInstance() {
        if (instance == null) {
            instance = new CardReaderControllerManager();
        }
        return instance;
    }

    /**
     * include::{userguide}/CRA_Overview.adoc[tag=CardReaderControllerManager_initializer]
     * @param cardReaderControllerInitializer - implementation of ICardReaderControllerInitializer
     * @return singleton instance of CardReaderControllerManager
     */
    public CardReaderControllerManager initialize(final ICardReaderControllerInitializer cardReaderControllerInitializer) {
        getCardReaderControllers()
                .forEach(cardReaderControllerInitializer::initializeCardReaderController);
        return this;
    }

    /**
     * Check if card reader provider found by the service loader
     * @return card reader provider found
     */
    public synchronized boolean cardReaderProviderAvailable() {
        return !getCardReaderProviders().isEmpty();
    }

    /**
     * Create a collection with all card reader provider names found by the service loader
     * @return collection of ard reader provider names
     */
    public synchronized Collection<String> getCardReaderProviderNames() {
        return getCardReaderProviders().parallelStream().map(provider -> provider.getClass().getName()).collect(Collectors.toList());
    }

    /**
     * Create a collection with all card reader provider descriptor objects found by the service loader
     * @return collection of ard reader provider descriptors
     */
    public synchronized Collection<IProviderDescriptor> getCardReaderProviderDescriptors() {
        return getCardReaderProviders().parallelStream().map(ICardReaderControllerProvider::getDescriptor).collect(Collectors.toList());
    }

    /**
     * Create a collection with all card reader found by card reader providers
     * @return collection of card reader
     */
    public synchronized Collection<ICardReader> getCardReaders() {
        return getCardReaderControllers().stream().map(StreamUtils.wrapFunction(ICardReaderController::getCardReaders))
                .flatMap(Collection::stream).collect(Collectors.toList());
    }

    /**
     * Create a collection with card reader controllers for all card reader providers
     * @return collection of ICardReaderController
     */
    public synchronized Collection<ICardReaderController> getCardReaderControllers() {
        return getCardReaderProviders().parallelStream().map(ICardReaderControllerProvider::getCardReaderController)
                .collect(Collectors.toList());
    }

    private synchronized Collection<ICardReaderControllerProvider> getCardReaderProviders() {
        List<ICardReaderControllerProvider> services = new ArrayList<>();
        ServiceLoader<ICardReaderControllerProvider> loader = ServiceLoader.load(ICardReaderControllerProvider.class);
        loader.forEach(services::add);
        return services;
    }

}
