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

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.Assert;
import org.junit.Test;

import de.gematik.ti.cardreader.provider.api.ICardReader;
import de.gematik.ti.cardreader.provider.api.ICardReaderController;
import de.gematik.ti.cardreader.provider.api.card.ICard;
import de.gematik.ti.cardreader.provider.api.card.ICardChannel;
import de.gematik.ti.cardreader.provider.api.command.CommandApdu;
import de.gematik.ti.cardreader.provider.api.command.ResponseApdu;
import de.gematik.ti.cardreader.provider.api.entities.IProviderDescriptor;
import de.gematik.ti.utils.codec.Hex;

/**
 * Unit-Tests for Class {@link CardReaderControllerManager}
 *
 */
public class CardReaderControllerManagerTest {

    public static final int SIM_PORT = 55555;

}
