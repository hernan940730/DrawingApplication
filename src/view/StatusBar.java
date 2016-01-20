package view;

import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusBar extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	
	private final JLabel name = new JLabel("Name:");
	private JLabel figName = new JLabel("                    ");
	private final JLabel posx = new JLabel("Pos X:");
	private JLabel figPosx = new JLabel("          ");
	private final JLabel posy = new JLabel("Pos Y:");
	private JLabel figPosy = new JLabel("          ");
	private final JLabel width = new JLabel("Widht:");
	private JLabel figWidth = new JLabel("          ");
	private final JLabel heigth = new JLabel("Heigth:");
	private JLabel figHeigth = new JLabel("          ");
	private final JLabel elements = new JLabel("Num elements:");
	private JLabel figElements = new JLabel("          ");
	private final JLabel length = new JLabel("Length:");
	private JLabel figLength = new JLabel("          ");
	
	private final JLabel mouse = new JLabel("Mouse:");
	private JLabel figMouse = new JLabel("          ");
	
	public StatusBar() {
		add(name);
		add(figName);
		add(posx);
		add(figPosx);
		add(posy);
		add(figPosy);
		add(width);
		add(figWidth);
		add(heigth);
		add(figHeigth);
		add(elements);
		add(figElements);
		add(length);
		add(figLength);
		add(mouse);
		add(figMouse);
	}
	
	@Override
	public void paint( Graphics g ) {
		super.paint( g );
	}

	public void setFigName( String figName ) {
		this.figName.setText(figName);
	}

	public void setFigPosx( String figPosx ) {
		this.figPosx.setText(figPosx);
	}

	public void setFigPosy( String figPosy ) {
		this.figPosy.setText(figPosy);
	}

	public void setFigWidth( String figWidth ) {
		this.figWidth.setText(figWidth);
	}

	public void setFigHeigth( String figHeigth ) {
		this.figHeigth.setText(figHeigth);
	}

	public void setFigElements( String figElements ) {
		this.figElements.setText(figElements);
	}

	public void setFigLength( String figLength ) {
		this.figLength.setText(figLength);
	}
	
	public void setFigMouse( String figMouse ) {
		this.figMouse.setText( figMouse );
	}
	
	public void setStatusBar(String name, String posx, String posy, String width, String heigth, 
			String elements, String length){
		
		setFigName(name + "     ");
		setFigElements(elements + "     ");
		setFigHeigth(heigth + "     ");
		setFigLength(length + "     ");
		setFigPosx(posx);
		setFigPosy(posy + "     ");
		setFigWidth(width + "     ");
	}
	
}
