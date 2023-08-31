import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ToDoListApp2 extends JFrame {

    private DefaultListModel<Task> taskListModel;
    private JList<Task> taskList;
    private JTextField taskInput;

    public ToDoListApp2() {
        // Set up the JFrame
        setTitle("To-Do List App");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a panel for the input field and "Add" button
        JPanel inputPanel = new JPanel();
        taskInput = new JTextField(20);
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new AddButtonListener());

        inputPanel.add(taskInput);
        inputPanel.add(addButton);

        // Create a list to display tasks
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setCellRenderer(new CheckboxListCellRenderer());

        // Add a double-click listener to toggle task completion
        taskList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedIndex = taskList.locationToIndex(e.getPoint());
                    Task selectedTask = taskListModel.getElementAt(selectedIndex);
                    selectedTask.setCompleted(!selectedTask.isCompleted());
                    taskList.repaint();
                }
            }
        });

        // Create a panel for the "Remove" button
        JPanel buttonPanel = new JPanel();
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new RemoveButtonListener());

        buttonPanel.add(removeButton);

        // Create a scroll pane for the task list
        JScrollPane scrollPane = new JScrollPane(taskList);

        // Add components to the main frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(inputPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private class Task {
        private String description;
        private boolean completed;

        public Task(String description) {
            this.description = description;
            this.completed = false; // Initially, tasks are not completed
        }

        public String getDescription() {
            return description;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String taskDescription = taskInput.getText().trim();
            if (!taskDescription.isEmpty()) {
                Task task = new Task(taskDescription);
                taskListModel.addElement(task);
                taskInput.setText("");
            } else {
                JOptionPane.showMessageDialog(ToDoListApp2.this, "Task cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class RemoveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int[] selectedIndices = taskList.getSelectedIndices();
            for (int i = selectedIndices.length - 1; i >= 0; i--) {
                taskListModel.removeElementAt(selectedIndices[i]);
            }
        }
    }

    class CheckboxListCellRenderer extends JCheckBox implements ListCellRenderer<Task> {
        public Component getListCellRendererComponent(JList<? extends Task> list, Task task, int index, boolean isSelected, boolean cellHasFocus) {
            setSelected(task.isCompleted());
            setText(task.getDescription());
            setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
            setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            return this;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ToDoListApp2 app = new ToDoListApp2();
            app.setVisible(true);
        });
    }
}
