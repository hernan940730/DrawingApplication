package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import model.IDrawingListener;


import model.Redo;
import model.Undo;
import controller.App;
import model.ReUnStrategy;

import javax.swing.border.LineBorder;

import java.awt.SystemColor;

public class MainFrame extends JFrame implements IDrawingListener{

	private static final long serialVersionUID = 1L;
	
	private Canvas canvas;
	protected StatusBar statusBar;
	
	private final int UNDO = 0;
	private final int REDO = 1;
	private JMenuBar menuBar;
	private JToolBar toolBar;
	private ReUnStrategy strategy[] = new ReUnStrategy[2];
	private JMenuItem previous ;
	private JMenuItem next;
	

	public MainFrame( String title ) throws HeadlessException {
		super( title );
		strategy[UNDO] = new Undo();
		strategy[REDO] = new Redo();
		setType(Type.NORMAL);
		setBounds(100, 100, 800, 600);
		setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );
//		setExtendedState( MAXIMIZED_BOTH );
		
		addWindowListener( new WindowAdapter() {
			@Override
			public void windowClosing( WindowEvent e ) {
				App.getInstance().quitDocument();
			}
		});
		
		
		canvas = new Canvas(this);
		canvas.setBackground( Color.WHITE );

		setContentPane(canvas);
		
		
		JMenu fileMenu = new JMenu( "File" );
		JMenu editMenu = new JMenu( "Edit" );
		JMenu toolMenu = new JMenu( "Tool" );
		JMenu helpMenu = new JMenu( "Help" );
		
		JMenuItem selTool = new JMenuItem( "Selection Tool" );
		JMenuItem linTool = new JMenuItem( "Line Tool" );
		JMenuItem ellTool = new JMenuItem( "Ellipse Tool" );
		JMenuItem recTool = new JMenuItem( "Rectangle Tool" );
		JMenuItem textTool = new JMenuItem( "Text Tool" );
		JMenuItem triangleTool = new JMenuItem( "Triangle Tool" );
		
		selTool.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.setCurTool(Canvas.SELECT);
			}
		});
		linTool.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.setCurTool(Canvas.LINE);
			}
		});
		ellTool.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.setCurTool(Canvas.ELLIPSE);
			}
		});
		recTool.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.setCurTool(Canvas.RECTANGLE);
			}
		});
		textTool.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.setCurTool(Canvas.TEXT);
			}
		});
		triangleTool.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.setCurTool(Canvas.TRIANGLE);
			}
		});
		
		
		JMenuItem newDocument = new JMenuItem( "New" );
		JMenuItem save = new JMenuItem( "Save" );
		JMenuItem load = new JMenuItem( "Load" );
		previous = new JMenuItem( "Un-Do" );
		next = new JMenuItem( "Re-Do" );
		JMenuItem quit = new JMenuItem( "Quit" );
		
		newDocument.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				App.getInstance().newDocument();
				
			}
		});
		
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				App.getInstance().saveDocument();
			}
		});

		load.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				App.getInstance().loadDocument();
			}
		});
		
		quit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				App.getInstance().quitDocument();
			}
		});
		
		JMenuItem group = new JMenuItem("Group");
		JMenuItem unGroup = new JMenuItem("un-group");
		JMenuItem remove = new JMenuItem("Remove");
		
		group.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				App.getInstance().group();
			}
		});
		
		unGroup.addActionListener(new ActionListener( ) {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				App.getInstance().unGroup();
			}
		});
		
		previous.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				strategy[UNDO].changeCanvas();
			}
		});
		
		previous.setEnabled( false );
		
		next.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				strategy[REDO].changeCanvas();
			}
		});
		
		next.setEnabled( false );
		
		remove.addActionListener(new ActionListener( ) {
					
				@Override
				public void actionPerformed(ActionEvent e) {
					App.getInstance().removeSelectedFigures();
				}
			}
		);
		canvas.setLayout(null);
		
		statusBar = new StatusBar();
		statusBar.setBounds(0, getBounds().height - 65, getBounds().width-20, 25);
		statusBar.setBorder(new LineBorder(new Color(0, 0, 0)));
		statusBar.setBackground(SystemColor.control);
		canvas.add(statusBar);
		
		fileMenu.add(newDocument);
		fileMenu.add(save);
		fileMenu.add(load);
		fileMenu.addSeparator();
		fileMenu.add(quit);
		
		editMenu.add( group );
		editMenu.add( unGroup );
		editMenu.addSeparator();
		editMenu.add(previous);
		editMenu.add(next);
		editMenu.addSeparator();
		editMenu.add( remove );
		
		toolMenu.add(selTool);
		toolMenu.addSeparator();
		toolMenu.add(linTool);
		toolMenu.add(ellTool);
		toolMenu.add(recTool);
		toolMenu.add(triangleTool);
		toolMenu.add(textTool);
		
		menuBar = new JMenuBar();
		menuBar.setBounds(332,16,122,21);
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(toolMenu);
		menuBar.add(helpMenu);

		getContentPane().add( menuBar );
		
		toolBar = new JToolBar("Figures");
		toolBar.setFloatable(false);
		toolBar.setToolTipText( "ToolBar" );
		toolBar.setBounds(getBounds().width - 60, menuBar.getBounds().height, 40, 260);
		toolBar.setOrientation(SwingConstants.VERTICAL);
		getContentPane().add(toolBar);

		JButton btnEllipse = new JButton( new ImageIcon( "textures/Ellipse.png" ) );
		btnEllipse.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed( ActionEvent e ) {
				canvas.setCurTool(Canvas.ELLIPSE); 
			}
		});
		
		JButton btnRectangle = new JButton(new ImageIcon("textures/Rectangle.png"));
		btnRectangle.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.setCurTool(Canvas.RECTANGLE);
			}
		});
		
		JButton btnLine = new JButton(new ImageIcon("textures/Line.png"));
		btnLine.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.setCurTool(Canvas.LINE);
			}
		});
		
		
		JButton btnText = new JButton(new ImageIcon("textures/Text.png"));
		btnText.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.setCurTool(Canvas.TEXT);
			}
		});
		
		
		JButton btnSelect = new JButton(new ImageIcon("textures/Select.png"));
		btnSelect.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.setCurTool(Canvas.SELECT);
			}
		});
		
		JButton btnRemove = new JButton(new ImageIcon("textures/Remove.png"));
		btnRemove.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				App.getInstance().removeSelectedFigures();
			}
		});
		
		JButton btnTriangle = new JButton(new ImageIcon("textures/Triangle.png"));
		btnTriangle.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.setCurTool(Canvas.TRIANGLE );
			}
		});
		
		toolBar.add(btnEllipse);
		toolBar.add(btnRectangle);
		toolBar.add(btnTriangle);
		toolBar.add(btnLine);
		toolBar.add(btnText);
		toolBar.add(btnSelect);
		toolBar.add(btnRemove);
		
	}

	@Override
	public void paint( Graphics g ) {
		super.paint( g );
		toolBar.setBounds(getBounds().width - 60, menuBar.getBounds().height, 40, toolBar.getComponentCount() * 42);
		statusBar.setBounds(0, getBounds().height - 65, getBounds().width-20, 25);
		menuBar.setBounds( 0, 0, getBounds().width, 20 );
		canvas.repaint();
		statusBar.repaint();
		
	}
	
	@Override
	public void processDrawingEvent( DrawingEvent de ) {
		switch(de){
			case MODIFIED:
				setTitle( getTitle() + "*" );
				break;
			case SAVED:
				setTitle( getTitle().substring( 0, getTitle().length() - 1 ) );
				break;
			case SELECTED:
				break;
			case UNDO:
				refreshUndoRedo();
				break;
			default:
				throw new UnsupportedOperationException( "MainFrame::processDrawingEvent(): " + de );
		}
	}
	
	public void refreshUndoRedo()
	{
		previous.setEnabled( App.getInstance().canUndo() );
		next.setEnabled( App.getInstance().canRedo() );
	}
	
	
	public Canvas getCanvas(){
		return canvas;
	} 
	
	public StatusBar getStatusBar(){
		return statusBar;
	}
}