package net.sf.anathema.character.generic.framework.magic.view;

import net.miginfocom.layout.CC;
import net.sf.anathema.interaction.Command;
import net.sf.anathema.interaction.Tool;
import net.sf.anathema.lib.file.RelativePath;
import net.sf.anathema.lib.gui.list.LegalityCheckListCellRenderer;
import net.sf.anathema.lib.gui.ui.ConfigurableListCellRenderer;
import net.sf.anathema.lib.util.Identifier;
import net.sf.anathema.swing.interaction.ActionInteraction;
import net.sf.anathema.view.interaction.AddToButton;
import org.jmock.example.announcer.Announcer;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

public class MagicLearnView implements IMagicLearnView {

  private final Announcer<IMagicViewListener> control = Announcer.to(IMagicViewListener.class);
  private final JList learnOptionsList = new JList(new DefaultListModel());
  private final JList learnedList = new JList(new DefaultListModel());
  private final List<JButton> centerButtons = new ArrayList<>();
  private final List<JButton> endButtons = new ArrayList<>();

  public void init(final IMagicLearnProperties properties) {
    learnOptionsList.setCellRenderer(new LegalityCheckListCellRenderer(properties.getLegalityCheck(), properties.getAvailableMagicRenderer()));
    learnOptionsList.setSelectionMode(SINGLE_SELECTION);
    ListCellRenderer renderer = new ConfigurableListCellRenderer(properties.getLearnedMagicRenderer());
    learnedList.setCellRenderer(renderer);
    final JButton addButton = createAddMagicButton(properties.getAddButtonIcon(), properties.getAddButtonToolTip());
    addOptionListListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        addButton.setEnabled(properties.isMagicSelectionAvailable(learnOptionsList.getSelectedValue()));
      }
    });
    final JButton removeButton = createRemoveMagicButton(properties.getRemoveButtonIcon(),
            properties.getRemoveButtonToolTip());
    centerButtons.add(addButton);
    centerButtons.add(removeButton);
    addSelectionListListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        List selectedValues = learnedList.getSelectedValuesList();
        removeButton.setEnabled(properties.isRemoveAllowed(selectedValues));
      }
    });
  }

  private JButton createAddMagicButton(RelativePath icon, String tooltip) {
    Command command = new Command() {
      @Override
      public void execute() {
        fireMagicAdded(learnOptionsList.getSelectedValuesList());
      }
    };
    return createButtonFromInteraction(icon, tooltip, command);
  }

  private JButton createRemoveMagicButton(RelativePath icon, String tooltip) {
    Command command = new Command() {
      @Override
      public void execute() {
        fireMagicRemoved(learnedList.getSelectedValuesList());
      }
    };
    return createButtonFromInteraction(icon, tooltip, command);
  }

  private JButton createButtonFromInteraction(RelativePath icon, String tooltip, Command command) {
    ActionInteraction interaction = new ActionInteraction();
    interaction.setIcon(icon);
    interaction.setTooltip(tooltip);
    interaction.setCommand(command);
    return createButton(interaction);
  }

  private JButton createButton(ActionInteraction tool) {
    tool.disable();
    JButton jButton = new JButton();
    tool.addTo(new AddToButton(jButton));
    return jButton;
  }

  private void fireMagicRemoved(List<Object> removedMagics) {
    Object[] objects = removedMagics.toArray(new Object[removedMagics.size()]);
    control.announce().magicRemoved(objects);
  }

  private void fireMagicAdded(List<Object> addedMagics) {
    Object[] objects = addedMagics.toArray(new Object[addedMagics.size()]);
    control.announce().magicAdded(objects);
  }

  @Override
  public void setMagicOptions(Object[] magics) {
    exchangeObjects((DefaultListModel) learnOptionsList.getModel(), magics);
  }

  private void exchangeObjects(DefaultListModel listModel, Object[] magic) {
    listModel.clear();
    for (Object spell : magic) {
      listModel.addElement(spell);
    }
  }

  @Override
  public void setLearnedMagic(Object[] magics) {
    exchangeObjects((DefaultListModel) learnedList.getModel(), magics);
  }

  @Override
  public void addMagicViewListener(IMagicViewListener listener) {
    control.addListener(listener);
  }

  public Tool addAdditionalTool() {
    ActionInteraction interaction = new ActionInteraction();
    JButton button = new JButton();
    endButtons.add(button);
    interaction.addTo(new AddToButton(button));
    return interaction;
  }

  public void addTo(JPanel panel) {
    panel.add(createScrollPane(learnOptionsList), new CC().grow().push());
    addButtonPanel(panel, centerButtons);
    panel.add(createScrollPane(learnedList), new CC().grow().push());
    List<JButton> buttons = endButtons;
    addButtonPanel(panel, buttons);
  }

  private void addButtonPanel(JPanel panel, List<JButton> buttons) {
    JPanel buttonPanel = new JPanel(new GridLayout(0, 1));
    for (JButton button : buttons) {
      buttonPanel.add(button);
    }
    panel.add(buttonPanel);
  }

  private JScrollPane createScrollPane(JList list) {
    JScrollPane scrollPane = new JScrollPane(list);
    scrollPane.setPreferredSize(new Dimension(200, 300));
    return scrollPane;
  }

  public ListModel getLearnedListModel() {
    return learnedList.getModel();
  }

  @Override
  public void clearSelection() {
    learnedList.clearSelection();
  }

  public void addSelectionListListener(ListSelectionListener listener) {
    learnedList.addListSelectionListener(listener);
  }

  public void addOptionListListener(ListSelectionListener listener) {
    learnOptionsList.addListSelectionListener(listener);
  }

  @Override
  public void addLearnedMagic(Object[] magics) {
    DefaultListModel listModel = (DefaultListModel) learnedList.getModel();
    for (Object spell : magics) {
      listModel.addElement(spell);
    }
  }

  @Override
  public void addMagicOptions(Identifier[] magics, Comparator<Identifier> comparator) {
    DefaultListModel listModel = (DefaultListModel) learnOptionsList.getModel();
    for (Identifier spell : magics) {
      boolean isInserted = false;
      for (int index = 0; index < listModel.getSize(); index++) {
        if (isInserted) {
          break;
        }
        Identifier magicOption = (Identifier) listModel.get(index);
        if (comparator.compare(spell, magicOption) < 0) {
          listModel.add(index, spell);
          isInserted = true;
          break;
        }
      }
      if (!isInserted) {
        listModel.addElement(spell);
      }
    }
  }

  @Override
  public void removeLearnedMagic(Object[] magics) {
    DefaultListModel listModel = (DefaultListModel) learnedList.getModel();
    for (Object spell : magics) {
      listModel.removeElement(spell);
    }
  }

  @Override
  public void removeMagicOptions(Object[] magics) {
    DefaultListModel listModel = (DefaultListModel) learnOptionsList.getModel();
    for (Object spell : magics) {
      listModel.removeElement(spell);
    }
  }
}