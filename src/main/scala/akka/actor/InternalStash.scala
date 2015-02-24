/*
 * Copyright (C) 2015 Red Bull Media House GmbH <http://www.redbullmediahouse.com> - all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package akka.actor

trait InternalStash extends Stash with StashFactory {
  private val theStash: StashSupport = createStash()

  /**
   * Internal API.
   */
  def internalStash(): Unit =
    theStash.stash()

  /**
   * Internal API.
   */
  def internalUnstash(): Unit =
    theStash.unstash()

  /**
   * Internal API.
   */
  def internalUnstashAll(): Unit =
    theStash.unstashAll()

  override def preRestart(reason: Throwable, message: Option[Any]): Unit =
    try internalUnstashAll() finally super.preRestart(reason, message)

  override def postStop(): Unit =
    try internalUnstashAll() finally super.postStop()
}
