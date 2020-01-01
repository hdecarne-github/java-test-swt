/*
 * Copyright (c) 2007-2020 Holger de Carne and contributors, All Rights Reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.carne.swt.widgets.heapinfo;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * Custom control displaying the current VM heap usage.
 */
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class HeapInfo extends Canvas implements PaintListener {

	private static final int BORDER_WIDTH = 1;
	private static final int MARGIN_WIDTH = 2;

	private int timer = 0;

	/**
	 * Constructs a new {@linkplain HeapInfo} instance.
	 *
	 * @param parent the control's parent.
	 * @param style the control's style (no styles supported yet).
	 */
	public HeapInfo(Composite parent, int style) {
		super(parent, checkStyle(style));
		addPaintListener(this);
	}

	private static int checkStyle(int style) {
		int checkedStyle = (style & (SWT.SHADOW_IN | SWT.SHADOW_OUT | SWT.SHADOW_NONE));

		if ((style & SWT.BORDER) == SWT.BORDER) {
			checkedStyle |= SWT.SHADOW_IN;
		} else if ((style & SWT.SHADOW_IN) != SWT.SHADOW_IN && (style & SWT.SHADOW_OUT) != SWT.SHADOW_OUT) {
			checkedStyle |= SWT.SHADOW_NONE;
		}
		return checkedStyle;
	}

	/**
	 * Sets the update timer frequency (in milliseconds).
	 * <p>
	 * A timer frequency of 0 or less disables the update timer.
	 *
	 * @param timer the timer frequency to set.
	 */
	public void setTimer(int timer) {
		int oldTimer = this.timer;

		this.timer = timer;
		if (oldTimer <= 0 && this.timer > 0) {
			getDisplay().timerExec(this.timer, this::onTimer);
		}
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		super.computeSize(wHint, hHint, changed);

		GC gc = new GC(this);
		Point size;

		try {
			int style = getStyle();

			size = gc.textExtent("0000B/0000B", SWT.NONE);
			if ((style & SWT.SHADOW_NONE) == SWT.SHADOW_NONE) {
				size.x += 2 * MARGIN_WIDTH;
				size.y += 2 * MARGIN_WIDTH;
			} else {
				size.x += 2 * MARGIN_WIDTH + 2 * BORDER_WIDTH;
				size.y += 2 * MARGIN_WIDTH + 2 * BORDER_WIDTH;
			}
		} finally {
			gc.dispose();
		}
		return size;
	}

	private static final char[] MEM_UNITS = { 'B', 'K', 'M', 'G', 'T', 'P', 'E', 'Z', 'Y' };

	@Override
	public void paintControl(PaintEvent evt) {
		Runtime runtime = Runtime.getRuntime();

		long total = runtime.totalMemory();
		long used = Math.max(0, total - runtime.freeMemory());
		int usage = (int) ((used * 100) / total);
		int totalMemUnitIndex = 0;
		int usedMemUnitIndex = 0;

		while (total > 1024 && totalMemUnitIndex < MEM_UNITS.length) {
			total >>= 10;
			totalMemUnitIndex++;
			if (used > 1024) {
				used >>= 10;
				usedMemUnitIndex++;
			}
		}

		int style = getStyle();
		int borderWidth = ((style & SWT.SHADOW_NONE) == SWT.SHADOW_NONE ? 0 : BORDER_WIDTH);
		Rectangle clientArea = getClientArea();
		int fillWidth = (clientArea.width * usage) / 100;
		Color defaultBackground = evt.gc.getBackground();
		Display display = getDisplay();
		GC gc = evt.gc;

		gc.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
		gc.fillRectangle(0, 0, fillWidth, clientArea.height);
		gc.setBackground(defaultBackground);
		gc.fillRectangle(fillWidth, 0, clientArea.width - fillWidth, clientArea.height);

		StringBuilder heapInfo = new StringBuilder();

		heapInfo.append(used).append(MEM_UNITS[usedMemUnitIndex]).append('/').append(total)
				.append(MEM_UNITS[totalMemUnitIndex]);

		gc.drawString(heapInfo.toString(), borderWidth + MARGIN_WIDTH, borderWidth + MARGIN_WIDTH, true);
		if ((style & SWT.SHADOW_IN) == SWT.SHADOW_IN) {
			Color ltColor = display.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW);
			Color rbColor = display.getSystemColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW);

			drawShadow(gc, ltColor, rbColor, clientArea.x, clientArea.y, clientArea.width - 1, clientArea.height - 1);
		} else if ((style & SWT.SHADOW_OUT) == SWT.SHADOW_OUT) {
			Color ltColor = display.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW);
			Color rbColor = display.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW);

			drawShadow(gc, ltColor, rbColor, clientArea.x, clientArea.y, clientArea.width - 1, clientArea.height - 1);
		}
	}

	private void drawShadow(GC gc, Color ltColor, Color rbColor, int lx, int ty, int rx, int by) {
		Color fgColor = gc.getForeground();

		gc.setLineWidth(BORDER_WIDTH);
		gc.setForeground(ltColor);
		gc.drawLine(lx, ty, rx, ty);
		gc.drawLine(lx, ty, lx, by);
		gc.setForeground(rbColor);
		gc.drawLine(lx, by, rx, by);
		gc.drawLine(rx, ty, rx, by);
		gc.setForeground(fgColor);
	}

	private void onTimer() {
		if (!isDisposed() && this.timer > 0) {
			if (isVisible()) {
				redraw();
			}
			getDisplay().timerExec(this.timer, this::onTimer);
		}
	}

}
