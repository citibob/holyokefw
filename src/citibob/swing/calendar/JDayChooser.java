/*
Holyoke Framework: library for GUI-based database applications
This file Copyright (c) 2006-2008 by Robert Fischer

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
/*
 *  JDayChooser.java  - A bean for choosing a day
 *  Copyright (C) 2004 Kai Toedter
 *  kai@toedter.com
 *  www.toedter.com
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
package citibob.swing.calendar;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import java.text.DateFormatSymbols;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * JCalendar is a bean for choosing a day.
 *
 * @author Kai Toedter
 * @version 1.2.1
 */
public class JDayChooser extends JPanel
implements ActionListener, KeyListener, FocusListener, MouseListener, CalModel.Listener {
    protected JButton[] days;
    protected JButton[] weeks;
    protected JButton selectedDay;
    protected JPanel weekPanel;
    protected JPanel dayPanel;
//    protected int day;
    protected Color oldDayBackgroundColor;
    protected Color selectedColor;
    protected Color sundayForeground;
    protected Color weekdayForeground;
    protected Color decorationBackgroundColor;
    protected String[] dayNames;
//    protected Calendar calendar;
//    protected Calendar today;
    protected Locale locale;
//    protected boolean initialized;
    protected boolean weekOfYearVisible;
    protected boolean decorationBackgroundVisible = true;
    protected boolean decorationBordersVisible;
//    private boolean alwaysFireDayProperty;

	
CalModel model;
int curYear;		// Current year displayed
int curMonth;		// Current month displayed

    /**
     * Default JDayChooser constructor.
     */
// ================================================================
// Initialization
public JDayChooser() {
	super();
	
	// Set up widget tree
	setBackground(Color.blue);
	this.locale = Locale.getDefault();
	days = new JButton[49];
	selectedDay = null;
	//calendar = Calendar.getInstance(locale);

	setLayout(new BorderLayout());

	dayPanel = new JPanel();
	dayPanel.setLayout(new GridLayout(7, 7));

	sundayForeground = new Color(164, 0, 0);
	weekdayForeground = new Color(0, 90, 164);

	// decorationBackgroundColor = new Color(194, 211, 252);
	// decorationBackgroundColor = new Color(206, 219, 246);
	decorationBackgroundColor = new Color(210, 228, 238);

	for (int y = 0; y < 7; y++) {
		for (int x = 0; x < 7; x++) {
			int index = x + (7 * y);

			if (y == 0) {
				// Create a button that doesn't react on clicks or focus
				// changes.
				// Thanks to Thomas Schaefer for the focus hint :)
				days[index] = new JButton() {
                                public void addMouseListener(MouseListener l) {
                                }

                                public boolean isFocusable() {
                                    return false;
                                }
						};

				days[index].setContentAreaFilled(decorationBackgroundVisible);
				days[index].setBorderPainted(decorationBordersVisible);
				days[index].setBackground(decorationBackgroundColor);
			} else {
				days[index] = new JButton("x");
				days[index].setFocusable(false);
//				days[index] = new JButton() {
////                               public void addMouseListener(MouseListener l) {
////                                }
//
//                                public boolean isFocusable() {
//                                    return false;
//                                }
//						};
				days[index].addKeyListener(this);
				days[index].addFocusListener(this);
//				days[index].addMouseListener(this);
				days[index].addActionListener(this);
			}

			days[index].setMargin(new Insets(0, 0, 0, 0));
			days[index].setFocusPainted(false);
			dayPanel.add(days[index]);
		}
	}

	weekPanel = new JPanel();
	weekPanel.setLayout(new GridLayout(7, 1));
	weeks = new JButton[7];

	for (int i = 0; i < 7; i++) {
		weeks[i] = new JButton() {
					public void addMouseListener(MouseListener l) {
					}

					public boolean isFocusable() {
						return false;
					}
				};
		weeks[i].setMargin(new Insets(0, 0, 0, 0));
		weeks[i].setFocusPainted(false);
		weeks[i].setBackground(decorationBackgroundColor);
		weeks[i].setForeground(new Color(100, 100, 100));

		weeks[i].setContentAreaFilled(decorationBackgroundVisible);
		weeks[i].setBorderPainted(decorationBordersVisible);

		if (i != 0) {
			weeks[i].setText("0" + (i + 1));
		}

		weekPanel.add(weeks[i]);
	}

	JButton testButton = new JButton();
	oldDayBackgroundColor = testButton.getBackground();
	selectedColor = new Color(160, 160, 160);
	
	add(dayPanel, BorderLayout.CENTER);
	
	setWeekOfYearVisible(true);
}

public void setModel(CalModel m)
{
	if (model != null) model.removeListener(this);
	this.model = m;
	model.addListener(this);
	redraw();
}

public void setLocale(Locale locale)
{
	this.locale = locale;
	redraw();
}

// ================================================================
// Draw the calendar
/**
 * Draws the day names of the day columnes.
 */
private void drawDayNames() {
	int firstDayOfWeek = model.getCal().getFirstDayOfWeek();
	DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(locale);
	dayNames = dateFormatSymbols.getShortWeekdays();

	int day = firstDayOfWeek;

	for (int i = 0; i < 7; i++) {
		days[i].setText(dayNames[day]);

		if (day == 1) days[i].setForeground(sundayForeground);
		else days[i].setForeground(weekdayForeground);

		if (day < 7) day++;
		else day -= 6;
	}
}
// --------------------------------------------------------
/**
 * Hides and shows the week buttons.
 */
protected void drawWeeks() {
	Calendar tmpCalendar = (Calendar) model.getCal().clone();

	for (int i = 1; i < 7; i++) {
		tmpCalendar.set(Calendar.DAY_OF_MONTH, (i * 7) - 6);

		int week = tmpCalendar.get(Calendar.WEEK_OF_YEAR);
		String buttonText = Integer.toString(week);

		if (week < 10) {
			buttonText = "0" + buttonText;
		}

		weeks[i].setText(buttonText);

		if ((i == 5) || (i == 6)) {
			weeks[i].setVisible(days[i * 7].isVisible());
		}
	}
}
// --------------------------------------------------------------
/**
 * Hides and shows the day buttons.
 */
protected void drawDays() {
	Calendar tmpCalendar = (Calendar)model.getCal().clone();
	int firstDayOfWeek = tmpCalendar.getFirstDayOfWeek();
	tmpCalendar.set(Calendar.DAY_OF_MONTH, 1);

	int firstDay = tmpCalendar.get(Calendar.DAY_OF_WEEK) - firstDayOfWeek;

	if (firstDay < 0) {
		firstDay += 7;
	}

	int i;

	for (i = 0; i < firstDay; i++) {
		days[i + 7].setVisible(false);
		days[i + 7].setText("");
	}

	tmpCalendar.add(Calendar.MONTH, 1);

	Date firstDayInNextMonth = tmpCalendar.getTime();
	tmpCalendar.add(Calendar.MONTH, -1);

	Date day = tmpCalendar.getTime();
	int n = 0;
	Color foregroundColor = getForeground();

	while (day.before(firstDayInNextMonth)) {
		days[i + n + 7].setText(Integer.toString(n + 1));
		days[i + n + 7].setVisible(true);

		if ((tmpCalendar.get(Calendar.DAY_OF_YEAR) == model.getCal().get(
					Calendar.DAY_OF_YEAR)) &&
				(tmpCalendar.get(Calendar.YEAR) == model.getCal().get(Calendar.YEAR))) {
			days[i + n + 7].setForeground(sundayForeground);
		} else {
			days[i + n + 7].setForeground(foregroundColor);
		}

		if ((n + 1) == model.getCal().get(Calendar.DAY_OF_MONTH)) {
			days[i + n + 7].setBackground(selectedColor);
			selectedDay = days[i + n + 7];
		} else {
			days[i + n + 7].setBackground(oldDayBackgroundColor);
		}

		n++;
		tmpCalendar.add(Calendar.DATE, 1);
		day = tmpCalendar.getTime();
	}

	for (int k = n + i + 7; k < 49; k++) {
		days[k].setVisible(false);
		days[k].setText("");
	}
}
/**
 * Sets the day.
 *
 * @param d the day
 *
 * @see #getDay
 */
public void drawCurrentDay()
{
	int d = getDay();
	// Check upper range
	Calendar tmpCalendar = (Calendar)model.getCal().clone();
	tmpCalendar.set(Calendar.DAY_OF_MONTH, 1);
	tmpCalendar.add(Calendar.MONTH, 1);
	tmpCalendar.add(Calendar.DATE, -1);

	int maxDaysInMonth = tmpCalendar.get(Calendar.DATE);

	if (d > maxDaysInMonth) {
		d = maxDaysInMonth;
	}


	if (selectedDay != null) {
		selectedDay.setBackground(oldDayBackgroundColor);
		selectedDay.repaint();
	}

	for (int i = 7; i < 49; i++) {
		if (days[i].getText().equals(Integer.toString(d))) {
			selectedDay = days[i];
			selectedDay.setBackground(selectedColor);

			break;
		}
	}
}
// =======================================================================
	
/**
 * Initializes both day names and weeks of the year.
 */
protected void initDecorations() {
	for (int x = 0; x < 7; x++) {
		days[x].setContentAreaFilled(decorationBackgroundVisible);
		days[x].setBorderPainted(decorationBordersVisible);
		weeks[x].setContentAreaFilled(decorationBackgroundVisible);
		weeks[x].setBorderPainted(decorationBordersVisible);
	}
}


	
// =======================================================================
/** Convenience: get the day from the model. */
int getDay()
	{ return model.getCal().get(Calendar.DAY_OF_MONTH); }

void setDay(int d)
{
	int oldDay = getDay();
	if (oldDay == d) return;		// Break infinite recursion.

//	if ((newDay >= 1) &&
//			(newDay <= calendar.getMaximum(Calendar.DAY_OF_MONTH))) {
//		setDay(newDay);
//	}

	
	model.set(Calendar.DAY_OF_MONTH, d);
}
// =======================================================================
void redraw()
{
	this.drawDays();
	this.drawWeeks();
	this.drawDayNames();	
	this.drawCurrentDay();
}


// ===================================================================
// CalModel.Listener
/**  Value has changed. */
public void calChanged()
{
	Calendar cal = model.getCal();
	int newYear = cal.get(Calendar.YEAR);
	int newMonth = cal.get(Calendar.MONTH);
	boolean monthChanged = (newYear != curYear || newMonth != curMonth);
	if (monthChanged) redraw();
	else this.drawCurrentDay();
}
public void dayButtonSelected() {}
public void nullChanged() { citibob.swing.WidgetTree.setEnabled(this, !model.isNull()); }
// ===================================================================
/**
 * Sets the font property.
 *
 * @param font the new font
 */
public void setFont(Font font) {
	if (days != null) {
		for (int i = 0; i < 49; i++) {
			days[i].setFont(font);
		}
	}
}

/**
 * Sets the foregroundColor color.
 *
 * @param foreground the new foregroundColor
 */
public void setForeground(Color foreground) {
	super.setForeground(foreground);

	if (days != null) {
		for (int i = 7; i < 49; i++) {
			days[i].setForeground(foreground);
		}

		drawDays();
	}
}

/**
 * Returns "JDayChooser".
 *
 * @return the name value
 */
public String getName() {
	return "JDayChooser";
}
// ====================================================================
int getButtonDay(Object c)
{
	JButton button = (JButton)c;
	String buttonText = button.getText();
	int day = Integer.parseInt(buttonText);
	return day;
}
// =====================================================================
    /**
     * JDayChooser is the FocusListener for all day buttons. (Added by Thomas
     * Schaefer)
     *
     * @param e the FocusEvent
     */
	/*
	 * Code below commented out by Mark Brown on 24 Aug 2004.  This code breaks
	 * the JDateChooser code by triggering the actionPerformed method on the next
	 * day button.  This causes the date chosen to always be incremented by one day.
	 */
    public void focusGained(FocusEvent e) {
//        JButton button = (JButton) e.getSource();
//        String buttonText = button.getText();
//
//        if ((buttonText != null) && !buttonText.equals("") && !e.isTemporary()) {
//            actionPerformed(new ActionEvent(e.getSource(), 0, null));
//        }
    }

    /**
     * Does nothing.
     *
     * @param e the FocusEvent
     */
    public void focusLost(FocusEvent e) {
    }

    /**
     * JDayChooser is the KeyListener for all day buttons. (Added by Thomas
     * Schaefer and modified by Austin Moore)
     *
     * @param e the KeyEvent
     */
    public void keyPressed(KeyEvent e) {
        int offset = (e.getKeyCode() == KeyEvent.VK_UP) ? (-7)
                                                        : ((e.getKeyCode() == KeyEvent.VK_DOWN)
            ? (+7)
            : ((e.getKeyCode() == KeyEvent.VK_LEFT) ? (-1)
                                                    : ((e.getKeyCode() == KeyEvent.VK_RIGHT)
            ? (+1) : 0)));

        int newDay = getDay() + offset;
		setDay(newDay);
    }

    /**
     * Does nothing.
     *
     * @param e the KeyEvent
     */
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Does nothing.
     *
     * @param e the KeyEvent
     */
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Enable or disable the JDayChooser.
     *
     * @param enabled The new enabled value
     */
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        for (short i = 0; i < days.length; i++) {
            if (days[i] != null) {
                days[i].setEnabled(enabled);
            }
        }

        for (short i = 0; i < weeks.length; i++) {
            if (weeks[i] != null) {
                weeks[i].setEnabled(enabled);
            }
        }
    }

    /**
     * In some Countries it is often usefull to know in which week of the year
     * a date is.
     *
     * @return boolean true, if the weeks of the year is shown
     */
    public boolean isWeekOfYearVisible() {
        return weekOfYearVisible;
    }

    /**
     * In some Countries it is often usefull to know in which week of the year
     * a date is.
     *
     * @param weekOfYearVisible true, if the weeks of the year shall be shown
     */
    public void setWeekOfYearVisible(boolean weekOfYearVisible) {
        if (weekOfYearVisible == this.weekOfYearVisible) {
            return;
        } else if (weekOfYearVisible) {
            add(weekPanel, BorderLayout.WEST);
        } else {
            remove(weekPanel);
        }

        this.weekOfYearVisible = weekOfYearVisible;
        validate();
        dayPanel.validate();
    }

    /**
     * Returns the day panel.
     *
     * @return the day panel
     */
    public JPanel getDayPanel() {
        return dayPanel;
    }

    /**
     * Returns the color of the decoration (day names and weeks).
     *
     * @return the color of the decoration (day names and weeks).
     */
    public Color getDecorationBackgroundColor() {
        return decorationBackgroundColor;
    }

    /**
     * Sets the background of days and weeks of year buttons.
     *
     * @param decorationBackgroundColor The background to set
     */
    public void setDecorationBackgroundColor(Color decorationBackgroundColor) {
        this.decorationBackgroundColor = decorationBackgroundColor;

        if (days != null) {
            for (int i = 0; i < 7; i++) {
                days[i].setBackground(decorationBackgroundColor);
            }
        }

        if (weeks != null) {
            for (int i = 0; i < 7; i++) {
                weeks[i].setBackground(decorationBackgroundColor);
            }
        }
    }

    /**
     * Returns the Sunday foreground.
     *
     * @return Color the Sunday foreground.
     */
    public Color getSundayForeground() {
        return sundayForeground;
    }

    /**
     * Returns the weekday foreground.
     *
     * @return Color the weekday foreground.
     */
    public Color getWeekdayForeground() {
        return weekdayForeground;
    }

    /**
     * Sets the Sunday foreground.
     *
     * @param sundayForeground The sundayForeground to set
     */
    public void setSundayForeground(Color sundayForeground) {
        this.sundayForeground = sundayForeground;
        drawDayNames();
        drawDays();
    }

    /**
     * Sets the weekday foreground.
     *
     * @param weekdayForeground The weekdayForeground to set
     */
    public void setWeekdayForeground(Color weekdayForeground) {
        this.weekdayForeground = weekdayForeground;
        drawDayNames();
        drawDays();
    }

    /**
     * Requests that the selected day also have the focus.
     */
    public void setFocus() {
        if (selectedDay != null) {
            this.selectedDay.requestFocus();
        }
    }

    /**
     * The decoration background is the background color of the day titles and
     * the weeks of the year.
     *
     * @return Returns true, if the decoration background is painted.
     */
    public boolean isDecorationBackgroundVisible() {
        return decorationBackgroundVisible;
    }

    /**
     * The decoration background is the background color of the day titles and
     * the weeks of the year.
     *
     * @param decorationBackgroundVisible true, if the decoration background
     *        shall be painted.
     */
    public void setDecorationBackgroundVisible(
        boolean decorationBackgroundVisible) {
        this.decorationBackgroundVisible = decorationBackgroundVisible;
        initDecorations();
    }

    /**
     * The decoration border is the button border of the day titles and the
     * weeks of the year.
     *
     * @return Returns true, if the decoration border is painted.
     */
    public boolean isDecorationBordersVisible() {
        return decorationBordersVisible;
    }

    /**
     * The decoration border is the button border of the day titles and the
     * weeks of the year.
     *
     * @param decorationBordersVisible true, if the decoration border shall be
     *        painted.
     */
    public void setDecorationBordersVisible(boolean decorationBordersVisible) {
        this.decorationBordersVisible = decorationBordersVisible;
        initDecorations();
    }
    
	
// ==================================================================
/**
 * JDayChooser is the ActionListener for all day buttons.
 *
 * @param e the ActionEvent
 */
public void actionPerformed(ActionEvent e) {
	this.setDay(getButtonDay(e.getSource()));
	model.fireDayButtonSelected();
}

// For use in pop-up and CitibobJTable --- will make work later.
int storedDay;
public void  mouseClicked(MouseEvent e)
{
}
public void  mouseEntered(MouseEvent e)
{
	int day = getButtonDay(e.getComponent());
	model.setTmpDay(day);
//	storedDay = getDay();
//	setDay(day);
}
public void  mouseExited(MouseEvent e)
{
	model.setTmpDay(-1);
//	setDay(storedDay);
}
public void  mousePressed(MouseEvent e) 
{
System.out.println("Mouse clicked!");
	int day = getButtonDay(e.getComponent());
//	storedDay = getDay();
	setDay(day);
//	JButton b = (JButton)e.getComponent();
//	b.setSelected(false);
	model.fireDayButtonSelected();
}
public void  mouseReleased(MouseEvent e)
{
//	System.out.println("Mouse Released!");
//	JButton b = (JButton)e.getComponent();
}
// ==================================================================
	
    /**
     * Creates a JFrame with a JDayChooser inside and can be used for testing.
     *
     * @param s The command line arguments
     */
    public static void main(String[] s) throws Exception {
        JFrame frame = new JFrame("JDayChooser");
        frame.getContentPane().add(new JDayChooser());
        frame.pack();
        frame.setVisible(true);
    }
}
