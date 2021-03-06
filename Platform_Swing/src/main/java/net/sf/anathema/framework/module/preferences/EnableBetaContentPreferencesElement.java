package net.sf.anathema.framework.module.preferences;

import net.sf.anathema.initialization.PreferenceElement;
import net.sf.anathema.initialization.reflections.Weight;
import net.sf.anathema.lib.util.Identifier;

import static net.sf.anathema.framework.presenter.action.preferences.IAnathemaPreferencesConstants.ENABLE_BETA_CONTENT;

@PreferenceElement
@Weight(weight = 70)
public class EnableBetaContentPreferencesElement extends AbstractCheckBoxPreferencesElement {

  private boolean enableBetaContent = SYSTEM_PREFERENCES.getBoolean(ENABLE_BETA_CONTENT, false);

  @Override
  protected boolean getBooleanParameter() {
    return enableBetaContent;
  }

  public static boolean enableBetaContent() {
    return SYSTEM_PREFERENCES.getBoolean(ENABLE_BETA_CONTENT, false);
  }

  @Override
  public void savePreferences() {
    SYSTEM_PREFERENCES.putBoolean(ENABLE_BETA_CONTENT, enableBetaContent);
  }

  @Override
  protected void setValue(boolean value) {
    enableBetaContent = value;
  }

  @Override
  protected String getLabelKey() {
    return "AnathemaCore.Tools.Preferences.EnableBetaContent";
  }

  @Override
  protected void resetValue() {
    enableBetaContent = SYSTEM_PREFERENCES.getBoolean(ENABLE_BETA_CONTENT, false);
  }

  @Override
  public Identifier getCategory() {
    return SYSTEM_CATEGORY;
  }
}