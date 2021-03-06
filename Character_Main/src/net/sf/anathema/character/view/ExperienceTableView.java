package net.sf.anathema.character.view;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import net.sf.anathema.character.presenter.advance.ExperienceUpdateListener;
import net.sf.anathema.character.view.advance.ExperienceConfigurationViewListener;
import net.sf.anathema.character.view.advance.ExperienceView;
import net.sf.anathema.character.view.advance.IExperienceViewProperties;
import net.sf.anathema.framework.swing.IView;
import net.sf.anathema.lib.gui.action.SmartAction;
import net.sf.anathema.lib.gui.icon.ImageProvider;
import net.sf.anathema.lib.gui.table.SmartTable;
import net.sf.anathema.lib.gui.table.columsettings.ITableColumnViewSettings;
import net.sf.anathema.lib.gui.table.columsettings.IntegerTableColumnSettings;
import net.sf.anathema.lib.gui.table.columsettings.StringTableColumnSettings;
import net.sf.anathema.lib.workflow.labelledvalue.view.LabelledIntegerValueView;
import org.jmock.example.announcer.Announcer;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import static net.sf.anathema.lib.gui.layout.LayoutUtils.fillWithoutInsets;

public class ExperienceTableView implements ExperienceView, IView {
  public static final int VALUE_INDEX = 1;
  public static final int DESCRIPTION_INDEX = 0;
  private final Announcer<ExperienceConfigurationViewListener> listeners = Announcer.to(ExperienceConfigurationViewListener.class);
  private SmartTable smartTable;
  private Action deleteAction;
  private LabelledIntegerValueView labelledIntValueView;
  private JPanel content = new JPanel(new MigLayout(fillWithoutInsets()));

  @Override
  public final void initGui(final IExperienceViewProperties properties) {
    String[] headers = new String[2];
    headers[ExperienceTableView.DESCRIPTION_INDEX] = properties.getDescriptionHeader();
    headers[ExperienceTableView.VALUE_INDEX] = properties.getPointHeader();
    DefaultTableModel tableModel = new DefaultTableModel(headers, 0);
    ITableColumnViewSettings[] settings = new ITableColumnViewSettings[]{new StringTableColumnSettings(), new IntegerTableColumnSettings(-10000, 10000, 5, Color.RED)};
    smartTable = new SmartTable(tableModel, settings);
    Icon addIcon = new ImageProvider().getImageIcon(properties.getAddIcon());
    smartTable.addAction(new SmartAction(addIcon) {
      @Override
      protected void execute(Component parentComponent) {
        fireAddRequested();
      }
    });
    Icon deleteIcon = new ImageProvider().getImageIcon(properties.getDeleteIcon());
    deleteAction = new SmartAction(deleteIcon) {
      @Override
      protected void execute(Component parentComponent) {
        smartTable.stopCellEditing();
        fireRemoveRequested();
      }
    };
    smartTable.addAction(deleteAction);
    smartTable.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        fireSelectionChanged();
      }
    });
    smartTable.createContent(content);
    content.setPreferredSize(new Dimension(700, 400));
    TableColumn descriptionColumn = smartTable.getTable().getTableHeader().getColumnModel().getColumn(0);
    descriptionColumn.setPreferredWidth(450);
    descriptionColumn.setWidth(descriptionColumn.getPreferredWidth());
    setRemoveButtonEnabled(false);
    JPanel totalPanel = new JPanel(new MigLayout(new LC().fillX()));
    labelledIntValueView = new LabelledIntegerValueView(properties.getTotalString(), 0, false, 7);
    labelledIntValueView.addComponents(totalPanel);
    labelledIntValueView.getValueLabel().setHorizontalAlignment(SwingConstants.RIGHT);
    content.add(totalPanel, new CC().growX().newline().alignY("top"));
  }

  @Override
  public final JComponent getComponent() {
    return content;
  }

  protected void fireSelectionChanged() {
    listeners.announce().selectionChanged(smartTable.getSelectedRowIndex());
  }

  private void fireRemoveRequested() {
    listeners.announce().removeRequested(smartTable.getSelectedRowIndex());
  }

  private void fireAddRequested() {
    listeners.announce().addRequested();
  }

  @Override
  public void addExperienceConfigurationViewListener(ExperienceConfigurationViewListener listener) {
    listeners.addListener(listener);
  }

  @Override
  public void setRemoveButtonEnabled(boolean enabled) {
    deleteAction.setEnabled(enabled);
  }

  @Override
  public void setTotalValueLabel(int overallExperiencePoints) {
    labelledIntValueView.setValue(overallExperiencePoints);
  }

  @Override
  public void addEntry(int experiencePoints, String text) {
    Object[] values = new Object[2];
    values[ExperienceTableView.VALUE_INDEX] = experiencePoints;
    values[ExperienceTableView.DESCRIPTION_INDEX] = text;
    getTableModel().addRow(values);
  }

  @Override
  public void clearEntries() {
    getTableModel().setRowCount(0);
  }

  @Override
  public void addUpdateListener(final ExperienceUpdateListener experienceUpdateListener) {
    getTableModel().addTableModelListener(new TableModelListener() {
      @Override
      public void tableChanged(TableModelEvent e) {
        if (e.getType() != TableModelEvent.UPDATE) {
          return;
        }
        int tableRowIndex = e.getFirstRow();
        Integer experiencePoints = (Integer) getTableModel().getValueAt(tableRowIndex, ExperienceTableView.VALUE_INDEX);
        String description = (String) getTableModel().getValueAt(tableRowIndex, ExperienceTableView.DESCRIPTION_INDEX);
        experienceUpdateListener.update(tableRowIndex, experiencePoints, description);
      }
    });
  }

  @Override
  public int getNumberOfEntriesOnDisplay() {
    return getTableModel().getRowCount();
  }

  private DefaultTableModel getTableModel() {
    return (DefaultTableModel) smartTable.getTable().getModel();
  }
}