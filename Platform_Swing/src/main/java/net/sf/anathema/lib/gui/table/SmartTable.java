package net.sf.anathema.lib.gui.table;

import com.google.common.base.Preconditions;
import net.miginfocom.layout.CC;
import net.sf.anathema.lib.gui.table.columsettings.ITableColumnViewSettings;
import org.jmock.example.announcer.Announcer;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

public class SmartTable {

  private boolean enabled = true;
  private final JTable table;
  private JPanel content;
  private final Announcer<ActionListener> selectionActionListeners = new Announcer<>(ActionListener.class);
  private final List<Action> actions = new ArrayList<>();

  public SmartTable(TableModel tableModel, ITableColumnViewSettings[] settings) {
    table = new JTable(tableModel);
    table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() != 2 || e.isMetaDown()) {
          return;
        }
        if (table.getSelectedRowCount() == 0) {
          return;
        }
        fireSelectionActionEvent();
      }
    });
    table.setRowHeight(Math.max(table.getRowHeight(), 21));
    TableColumnConfigurator.configureTableColumns(table, settings);
    table.setSelectionMode(SINGLE_SELECTION);
  }

  public void addAction(Action action) {
    Preconditions.checkArgument(content == null, "Adding actions after creating content.");
    actions.add(action);
  }

  public JTable getTable() {
    return table;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
    updateEnabled();
  }

  protected void updateEnabled() {
    table.setEnabled(enabled);
    if (!enabled) {
      table.getSelectionModel().clearSelection();
    }
    for (Action action : actions) {
      action.setEnabled(enabled);
    }
  }

  public final void createContent(JPanel content) {
    JPanel tablePanel = new JPanel(new BorderLayout());
    tablePanel.add(table);
    JScrollPane scrollPane = new JScrollPane(tablePanel) {
      @Override
      public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        SmartTable.this.setEnabled(enabled);
      }
    };
    scrollPane.setColumnHeaderView(table.getTableHeader());
    content.add(scrollPane, new CC().grow().push());
    for (Action action : actions) {
      content.add(new JButton(action), new CC().flowY().alignY("top").split());
    }
    updateEnabled();
  }

  private void fireSelectionActionEvent() {
    ActionEvent actionEvent = new ActionEvent(table, -1, "select");
    selectionActionListeners.announce().actionPerformed(actionEvent);
  }

  public void stopCellEditing() {
    TableCellEditor cellEditor = getTable().getCellEditor();
    if (cellEditor != null) {
      cellEditor.stopCellEditing();
    }
  }

  public int getSelectedRowIndex() {
    return table.getSelectedRow();
  }
}