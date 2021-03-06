package net.sf.anathema.swing.interaction;

import net.sf.anathema.framework.view.menu.AddToSwingComponent;
import net.sf.anathema.interaction.AcceleratorMap;
import net.sf.anathema.interaction.CommandProxy;
import net.sf.anathema.interaction.Hotkey;
import net.sf.anathema.interaction.ProxyAcceleratorMap;
import net.sf.anathema.interaction.Tool;
import net.sf.anathema.lib.file.RelativePath;
import net.sf.anathema.lib.gui.CommandAction;
import net.sf.anathema.lib.gui.action.SmartAction;
import net.sf.anathema.lib.gui.icon.ImageProvider;

public class ActionInteraction implements Tool {

  private final CommandProxy commandProxy = new CommandProxy();
  private final SmartAction action = new CommandAction(commandProxy);
  private final ProxyAcceleratorMap acceleratorMap = new ProxyAcceleratorMap();

  @Override
  public void setIcon(RelativePath relativePath) {
    action.setIcon(new ImageProvider().getImageIcon(relativePath));
  }

  @Override
  public void setOverlay(RelativePath relativePath) {
    throw new UnsupportedOperationException("Urs: We should never need this.");
  }

  @Override
  public void setTooltip(String text) {
    action.setToolTipText(text);
  }

  @Override
  public void setText(String text) {
    action.setName(text);
  }

  @Override
  public void enable() {
    action.setEnabled(true);
  }

  @Override
  public void disable() {
    action.setEnabled(false);
  }

  @Override
  public void setCommand(net.sf.anathema.interaction.Command command) {
    commandProxy.setDelegate(command);
  }

  @Override
  public void setHotkey(Hotkey hotkey) {
    acceleratorMap.register(hotkey, commandProxy);
  }

  public void registerHotkeyIn(AcceleratorMap acceleratorMap) {
    this.acceleratorMap.setActualMap(acceleratorMap);
  }

  public void addTo(AddToSwingComponent addTo) {
    addTo.add(action);
  }
}