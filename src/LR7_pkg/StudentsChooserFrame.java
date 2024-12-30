package LR7_pkg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;


public class StudentsChooserFrame extends JFrame {
	
	public StudentsChooserFrame() {
		super("Students List");
		setSize(500, 500);
		setLocation(200, 100);
		setLayout(new BorderLayout());
		
		//Пока создадим эти методы пустыми
		createMenubar();
		createToolbar();
		createListsPanel();
		createToolbarListeners();
		createButtonsListeners();
		createButtonsPanel();

	}
		
	
	private void createMenubar() {
		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);
		JMenu fileMenu = new JMenu("File");
		menubar.add(fileMenu);
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
		exitItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent	e) {
				int option = JOptionPane.showConfirmDialog(getParent(),
						"Are you sure you want to quit?", "Confirm quit",
						JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE, null);
				if (option == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			}
		});
		
		fileMenu.add(exitItem);
	}
	
	
	
	private JButton resetButton, saveButton;

	private void createToolbar() {
		saveButton = new JButton("Save");
		resetButton = new JButton("Reset");
		JPanel toolbar = new JPanel();
		toolbar.setLayout(new FlowLayout());
		toolbar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		toolbar.add(saveButton);
		toolbar.add(resetButton);
		add(toolbar, BorderLayout.NORTH);
	}

	
	
	private DefaultListModel<String> studentsModel, teamModel;
	private JList<String> studentsList, teamList;

	//private JPanel createButtonsPanel() {
	//	return new JPanel();
	//}
	
	private JButton takeButton, returnButton, takeAllButton, returnAllButton;
	private JPanel createButtonsPanel() {
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		buttonsPanel.setLayout(new GridLayout(4, 0));
		takeButton = new JButton(">");
		takeButton.setToolTipText("Добавить выделенных людей");
		takeAllButton = new JButton(">>");
		takeAllButton.setToolTipText("Добавить всех");
		returnButton = new JButton("<");
		returnButton.setToolTipText("Вернуть выделенных людей");
		returnAllButton = new JButton("<<");
		returnAllButton.setToolTipText("Вернуть всеx");
		buttonsPanel.add(takeButton);
		buttonsPanel.add(takeAllButton);
		buttonsPanel.add(returnButton);
		buttonsPanel.add(returnAllButton);
		return buttonsPanel;
	}

	private void createListsPanel() {
		studentsModel = new DefaultListModel<String>();
		for (String student : StudentsBase.getStudents()) {
			studentsModel.addElement(student);
		}
		
		teamModel = new DefaultListModel<String>();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout());
		studentsList = new JList<String>(studentsModel);
		studentsList.setToolTipText("Доступные студенты");
		studentsList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		teamList = new JList<String>(teamModel);
		teamList.setToolTipText("Выбранные студенты");
		teamList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.add(studentsList);
		panel.add(createButtonsPanel());
		panel.add(teamList);
		add(panel, BorderLayout.CENTER);
		
	}
	
	

	private void createToolbarListeners() {
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String all = "";
				Enumeration<String> elements = teamModel.elements();
				while (elements.hasMoreElements()) {
					all += elements.nextElement() + "\n";
				}
				JOptionPane.showMessageDialog(getParent(), all,
						"Выбраны следующие студенты:",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}
	
	
	private void createButtonsListeners() {
		takeButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			List<String> selection = studentsList.getSelectedValuesList();
			for (String student : selection) {
				teamModel.addElement(student);
			}
			for (String student : selection) {
				studentsModel.removeElement(student);
			}
		}
		});		

		returnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				List<String> selection = teamList.getSelectedValuesList();
				for (String student : selection) {
					studentsModel.addElement(student);
				}
				for (String student : selection) {
					teamModel. removeElement(student);
				}
			}
		});
		
		
		takeAllButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Enumeration<String> elements = studentsModel.elements();
				while (elements.hasMoreElements()) {
				String next = elements.nextElement();
				teamModel.addElement(next);
				}
				studentsModel.removeAllElements();
			}
		});
		
		
		returnAllButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Enumeration<String> elements = teamModel.elements();
				while (elements.hasMoreElements()) {
					String next = elements.nextElement();
					studentsModel.addElement(next);
				}
				teamModel.removeAllElements();
			}
		});
		
		
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				teamModel.removeAllElements();
				studentsModel.removeAllElements();
				for (String student : StudentsBase.getStudents()) {
					studentsModel.addElement(student);
				}
			}
		});
	}
	
	
	
}

