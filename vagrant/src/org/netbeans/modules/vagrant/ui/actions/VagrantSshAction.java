/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2013 Sun Microsystems, Inc.
 */
package org.netbeans.modules.vagrant.ui.actions;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import org.netbeans.api.project.Project;
import org.netbeans.modules.dlight.api.terminal.TerminalSupport;
import org.netbeans.modules.nativeexecution.api.ExecutionEnvironment;
import org.netbeans.modules.nativeexecution.api.ExecutionEnvironmentFactory;
import org.netbeans.modules.vagrant.command.InvalidVagrantExecutableException;
import org.netbeans.modules.vagrant.command.SshInfo;
import org.netbeans.modules.vagrant.command.Vagrant;
import org.netbeans.modules.vagrant.utils.VagrantUtils;
import org.openide.util.NbBundle.Messages;

@Messages("CTL_VagrantSshAction=Vagrant ssh")
public final class VagrantSshAction extends VagrantAction {

    private static final long serialVersionUID = -3351388651004111053L;
    private static final Logger LOGGER = Logger.getLogger(VagrantSshAction.class.getName());

    public VagrantSshAction() {
        super(Bundle.CTL_VagrantSshAction(), VagrantUtils.getIcon(VagrantUtils.SSH_ICON_16));
    }

    @Override
    public void actionPerformed(final Project project) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    // vagrant ssh command is not run
                    // instead, remote terminal is opened
                    Vagrant vagrant = Vagrant.getDefault();
                    SshInfo sshInfo = vagrant.getSshInfo(project);
                    if (sshInfo != null) {
                        ExecutionEnvironment executionEnvironment = ExecutionEnvironmentFactory.createNew(
                                sshInfo.getUser(),
                                sshInfo.getHostName(),
                                sshInfo.getPort());
                        TerminalSupport.openTerminal("Vagrant", executionEnvironment, null); // NOI18N
                    }
                } catch (InvalidVagrantExecutableException ex) {
                    LOGGER.log(Level.WARNING, ex.getMessage());
                }
            }
        });
    }
}
