package net.sf.anathema.lib.gui.wizard;

import com.google.common.base.Preconditions;
import net.disy.commons.core.message.BasicMessage;
import net.disy.commons.core.message.IBasicMessage;
import net.disy.commons.core.model.listener.ListenerList;
import net.disy.commons.swing.dialog.core.IDialogHelpHandler;
import net.disy.commons.swing.dialog.input.IRequestFinishListener;
import net.disy.commons.swing.dialog.wizard.IWizardConfiguration;
import net.disy.commons.swing.dialog.wizard.IWizardPage;
import net.sf.anathema.lib.gui.wizard.workflow.CheckInputListener;
import net.sf.anathema.lib.gui.wizard.workflow.ICondition;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractAnathemaWizardPage implements IAnathemaWizardPage {

  private IBasicMessage message = new BasicMessage("!!! UNSET-MESSAGE !!!");
  private final ListenerList<IRequestFinishListener> requestFinishListeners = new ListenerList<IRequestFinishListener>();
  private IWizardConfiguration wizard = null;
  private IAnathemaWizardPage previousPage = null;
  protected final Map<ICondition, IAnathemaWizardPage> followUpPagesByCondition = new HashMap<ICondition, IAnathemaWizardPage>();

  @Override
  public boolean canFlipToNextPage() {
    return getNextPage() != null;
  }

  @Override
  public final IAnathemaWizardPage getPreviousPage() {
    return previousPage;
  }

  protected final void addFollowupPage(IAnathemaWizardPage page, CheckInputListener inputListener, ICondition condition) {
    followUpPagesByCondition.put(condition, page);
    page.initPresentation(inputListener);
  }

  @Override
  public final IWizardPage getNextPage() {
    for (ICondition condition : followUpPagesByCondition.keySet()) {
      if (condition.isFulfilled()) {
    	  IWizardPage page = followUpPagesByCondition.get(condition);
    	  if (page instanceof IAnathemaWizardPage)
    		  ((IAnathemaWizardPage)page).setPreviousPage(this);
          return page;
      }
    }
    return null;
  }

  @Override
  public final void setPreviousPage(IAnathemaWizardPage page) {
    this.previousPage = page;
  }

  @Override
  public final void initPresentation(CheckInputListener inputListener) {
    initModelListening(inputListener);
    addFollowUpPages(inputListener);
    initPageContent();
  }

  protected abstract void initPageContent();

  protected abstract void addFollowUpPages(CheckInputListener inputListener);

  protected abstract void initModelListening(CheckInputListener inputListener);

  @Override
  public boolean canCancel() {
    return true;
  }

  @Override
  public IWizardConfiguration getWizard() {
    return wizard;
  }

  @Override
  public void initializeFromData() {
  }

  @Override
  public void addRequestFinishListener(
      IRequestFinishListener requestFinishListener) {
    requestFinishListeners.add(requestFinishListener);
  }

  @Override
  public void removeRequestFinishListener(
      IRequestFinishListener requestFinishListener) {
    requestFinishListeners.remove(requestFinishListener);
  }

  @Override
  public void enter() {
  }

  @Override
  public void leave() {
  }

  @Override
  public String getTitle() {
    return getDescription();
  }

  @Override
  public IDialogHelpHandler getHelpHandler() {
    return null;
  }

  @Override
  public IBasicMessage getMessage() {
    return message;
  }

  @Override
  public void setMessage(IBasicMessage message) {
    Preconditions.checkNotNull(message);
    this.message = message;
  }
}