/*
 * Licensed to the University of California, Berkeley under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package tachyon.shell.command;

import java.io.IOException;

import tachyon.TachyonURI;
import tachyon.client.file.TachyonFileSystem;
import tachyon.conf.TachyonConf;
import tachyon.exception.TachyonException;

/**
 * Mounts a UFS path onto a Tachyon path.
 */
public class MountCommand extends AbstractTfsShellCommand {

  public MountCommand(TachyonConf conf, TachyonFileSystem tfs) {
    super(conf, tfs);
  }

  @Override
  public String getCommandName() {
    return "mount";
  }

  @Override
  protected int getNumOfArgs() {
    return 2;
  }

  @Override
  public void run(String... args) throws IOException {
    TachyonURI tachyonPath = new TachyonURI(args[0]);
    TachyonURI ufsPath = new TachyonURI(args[1]);
    try {
      if (mTfs.mount(tachyonPath, ufsPath)) {
        System.out.println("Mounted " + ufsPath + " at " + tachyonPath);
      } else {
        throw new IOException("mount: Failed to mount" + ufsPath + " to " + tachyonPath);
      }
    } catch (TachyonException e) {
      throw new IOException(e.getMessage());
    }
  }

}
