package net.sf.anathema.framework.swing;

import net.sf.anathema.lib.exception.IExceptionHandler;
import net.sf.anathema.lib.gui.dialog.message.MessageDialogFactory;
import net.sf.anathema.lib.logging.Logger;
import net.sf.anathema.lib.message.Message;
import net.sf.anathema.lib.resources.Resources;

import javax.swing.JOptionPane;

public class SwingDialogExceptionHandler implements IExceptionHandler {

  private static final Logger logger = Logger.getLogger(SwingDialogExceptionHandler.class);
  private final Resources resources;

  public SwingDialogExceptionHandler(Resources resources) {
    this.resources = resources;

  }

  @Override
  public void handle(Throwable exception) {
    Logger.getLogger(getClass()).error("Uncaught Exception", exception);
    if (exception instanceof Exception) {
      indicateException((Exception) exception);
    }
    else {
      indicateError(exception);
    }
  }

  protected void indicateError(Throwable exception) {
    logger.error(exception);
    String message = getString("CentralExceptionHandling.ErrorOccured.Message");
    String title = getString("CentralExceptionHandling.ErrorOccured.Title");
    int answer = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
    if (answer == JOptionPane.YES_OPTION) {
      System.exit(0);
    }
  }

  private String getString(String resourceKey) {
    return resources.getString(resourceKey);
  }

  protected void indicateException(Exception exception) {
    String message = getString("CentralExceptionHandling.ExceptionOccured.Message");
    logger.error(message, exception);
    MessageDialogFactory.showMessageDialog(JOptionPane.getRootFrame(), new Message(message, exception));
  }
}