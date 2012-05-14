/**
 * Copyright (C) 2005, 2011 disy Informationssysteme GmbH and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
package net.disy.commons.swing.dialog.wizard;

import net.disy.commons.swing.dialog.DisyCommonsSwingDialogMessages;
import net.disy.commons.swing.dialog.core.DialogHeaderPanelConfiguration;
import net.disy.commons.swing.dialog.core.IDialogResult;
import net.disy.commons.swing.dialog.core.IVetoDialogCloseHandler;
import net.disy.commons.swing.dialog.core.internal.AbstractGenericDialogConfiguration;
import net.disy.commons.swing.dialog.userdialog.buttons.DialogButtonConfigurationFactory;

import java.awt.Component;

/**
 * Abstract superclass that can be extended for {@link IWizardConfiguration} implementations.
 */
public abstract class AbstractWizardConfiguration extends AbstractGenericDialogConfiguration
    implements
    IWizardConfiguration {
  private IWizardContainer container;

  public AbstractWizardConfiguration() {
    super(
        DialogButtonConfigurationFactory
            .createOkCancelWithOkText(DisyCommonsSwingDialogMessages.WIZARD_FINISH),
        DialogHeaderPanelConfiguration.createVisibleWithoutIcon());
  }

  @Override
  public IWizardContainer getContainer() {
    return container;
  }

  @Override
  public void setContainer(final IWizardContainer container) {
    this.container = container;
  }

  @Override
  public boolean canCancel() {
    final IWizardPage currentPage = getContainer().getCurrentPage();
    return currentPage.canCancel();
  }

  @Override
  public boolean canFinish() {
    final IWizardPage currentPage = getContainer().getCurrentPage();
    return currentPage.canFinish();
  }

  @Override
  public boolean shallInitializePagesFromData() {
    return false;
  }

  @Override
  public IVetoDialogCloseHandler getVetoCloseHandler() {
    return new IVetoDialogCloseHandler() {
      @Override
      public boolean handleDialogAboutToClose(
          final IDialogResult result,
          final Component parentComponent) {
        if (result.isCanceled()) {
          return true;
        }
        return performFinish(parentComponent);
      }
    };
  }

  @Deprecated
  @Override
  public boolean performFinish(final Component parentComponent) {
    return true;
  }
}