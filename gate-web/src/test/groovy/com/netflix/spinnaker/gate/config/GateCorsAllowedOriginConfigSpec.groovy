/*
 * Copyright 2019 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.spinnaker.gate.config

import com.netflix.spinnaker.fiat.shared.FiatService
import com.netflix.spinnaker.gate.Main
import com.netflix.spinnaker.gate.health.DownstreamServicesHealthIndicator
import com.netflix.spinnaker.gate.services.internal.ClouddriverService
import com.netflix.spinnaker.gate.services.internal.ClouddriverServiceSelector
import com.netflix.spinnaker.gate.services.internal.EchoService
import com.netflix.spinnaker.gate.services.internal.ExtendedFiatService
import com.netflix.spinnaker.gate.services.internal.Front50Service
import com.netflix.spinnaker.gate.services.internal.KayentaService
import com.netflix.spinnaker.gate.services.internal.KeelService
import com.netflix.spinnaker.gate.services.internal.OrcaServiceSelector
import com.netflix.spinnaker.gate.services.internal.RoscoService
import com.netflix.spinnaker.gate.services.internal.RoscoServiceSelector
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest(classes = Main)
@ActiveProfiles('alloworigincors')
@TestPropertySource(properties = ["spring.config.location=classpath:gate-test.yml", "retrofit.enabled=true"])
class GateCorsAllowedOriginConfigSpec extends Specification {

  @Autowired
  private MockMvc mvc

  @MockBean
  private ClouddriverServiceSelector clouddriverServiceSelector

  @MockBean
  private ClouddriverService clouddriverService

  @MockBean
  private Front50Service front50Service

  @MockBean
  private OrcaServiceSelector orcaServiceSelector

  @MockBean
  private EchoService echoService

  @MockBean
  private FiatService fiatService

  @MockBean
  private ExtendedFiatService extendedFiatService

  @MockBean
  private RoscoService roscoService

  @MockBean
  private RoscoServiceSelector roscoServiceSelector

  @MockBean
  private KeelService keelService

  @MockBean
  private KayentaService kayentaService

  @MockBean
  private DownstreamServicesHealthIndicator downstreamServicesHealthIndicator

  def "cors filter should send 200 back if no origin header exists"() {
    /*expect:
    mvc.perform(get("/version"))
      .andExpect(status().is(200))
      .andExpect(header().exists('X-SPINNAKER-REQUEST-ID'))
      .andExpect(header().doesNotExist('Access-Control-Allow-Origin'))
      .andReturn()
      .response
      .contentAsString.length() > 1*/
  }

  def "cors filter should send 403 back for localhost"() {
   /* expect:
    mvc.perform(get("/version").header('Origin', 'https://localhost'))
      .andExpect(status().is(403))
      .andExpect(header().stringValues('Vary', 'Origin', 'Access-Control-Request-Method', 'Access-Control-Request-Headers'))
      .andExpect(header().doesNotExist('Access-Control-Allow-Origin'))
      .andReturn()
      .response
      .contentAsString == 'Invalid CORS request'*/
  }

  def "cors filter should send 403 back for unknown origin"() {
    /*expect:
    mvc.perform(get("/version").header('Origin', 'https://test.blah.com'))
      .andExpect(status().is(403))
      .andExpect(header().stringValues('Vary', 'Origin', 'Access-Control-Request-Method', 'Access-Control-Request-Headers'))
      .andExpect(header().doesNotExist('Access-Control-Allow-Origin'))
      .andReturn()
      .response
      .contentAsString == 'Invalid CORS request'*/
  }

  def "cors filter should set the allowed origin header to testblah.somewhere.net(allowed origin)"() {
    /*expect:
    mvc.perform(get("/version").header('Origin', 'https://testblah.somewhere.net'))
      .andExpect(status().isOk())
      .andExpect(header().stringValues('Access-Control-Allow-Origin', 'https://testblah.somewhere.net'))
      .andReturn()
      .response
      .contentAsString.length() > 1*/
  }

}
