package gui;

import Chart.Chart;
import Chart.DateChart;
import Chart.GamesPlayedPerMonthChart;
import exceptions.MapTypeNotFoundException;
import exceptions.SummonerNotFoundException;
import maptypes.ChampMap;
import maptypes.DateMap;
import maptypes.GameMap;
import maptypes.MapFactory;
import replay.StatFile;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Mainwindow extends JFrame {

	private JPanel contentPane;
	private JTextField names;
	private JList list;
	private JMenuBar menuBar;
	private JProgressBar progressBar;
	private JButton generate;
	private JScrollPane scrollPane;
	private JLabel lblNewLabel;
	private JTabbedPane ChartPanel;
	private DefaultListModel<String> listmodel;

    ChampMap lol;
    DateMap date;
    GameMap gaem;
    DateChart chart;
    GamesPlayedPerMonthChart gaemchart;

	
	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
//		new StatSaver().ReplaysToStatsFile(new File("E:\\Dateien\\Eigene Dokumente\\Online\\Lol Replays"), new File("l"));
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mainwindow frame = new Mainwindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public Mainwindow() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1033, 661);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("Icon.png")));
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		scrollPane = new JScrollPane();
		
		progressBar = new JProgressBar();
		
		generate = new JButton(">_");
		generate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generate.setEnabled(false);
				new Thread(new Runnable() {
					

					@Override
					public void run() {
						ChartPanel.removeAll();

						try {
							listmodel.clear();
							MapFactory map =  new MapFactory(progressBar);
                            lol = (ChampMap) map.getMap(names.getText().split(";"), null,new File("E:\\Dateien\\Eigene Dokumente\\Online\\Lol Replays"), MapFactory.Maps.ChampMap);
                            date = (DateMap) map.getMap(names.getText().split(";"), new StatFile(new File("D:\\Dropbox\\stats.stats")),null, MapFactory.Maps.TimeMap);
                            gaem = (GameMap) map.getMap(names.getText().split(";"), new StatFile(new File("D:\\Dropbox\\stats.stats")),null, MapFactory.Maps.GameMap);

						    new Chart(lol.getChampionMap(),ChartPanel);
							chart = new DateChart(date.getMap(),ChartPanel);
						    gaemchart = new GamesPlayedPerMonthChart(gaem.getMap(), ChartPanel);
					        ChartPanel.revalidate();
					        ChartPanel.repaint();
					        lblNewLabel.setText(map.getStatistics());
					        fillList(lol);
					        generate.setEnabled(true);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SummonerNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (MapTypeNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
				}).start();

			}
		});
		
		
		names = new JTextField();
		names.setColumns(10);
		
		
		lblNewLabel = new JLabel("");
		
		ChartPanel = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
						.addComponent(names, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
						.addComponent(generate, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(ChartPanel, GroupLayout.DEFAULT_SIZE, 858, Short.MAX_VALUE)
						.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 863, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(names, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(generate)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE))
						.addComponent(ChartPanel, GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(0))
		);
		
		listmodel = new DefaultListModel<String>();
		list = new JList(listmodel);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				Object[] test = list.getSelectedValues();
				ArrayList<String> list = new ArrayList<String>();
				for(Object st : test)
				{
					list.add(st.toString().replaceAll("[^a-zA-Z]", ""));
					System.out.println(st.toString().replaceAll("[^a-zA-Z]", ""));
				}

                chart.reloadChart(list);
			}
		});
		
		
		scrollPane.setViewportView(list);
		contentPane.setLayout(gl_contentPane);
	}
	
	
	
	private void fillList(ChampMap map)
	{
		for(Map.Entry<String, Integer> entry : map.getChampionMap().entrySet())
		{
			listmodel.addElement(entry.getKey() + " - [" + entry.getValue()+"]");
		}
	}
}
