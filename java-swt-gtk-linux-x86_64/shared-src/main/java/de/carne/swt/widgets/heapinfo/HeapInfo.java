/*
 * Copyright (c) 2007-2018 Holger de Carne and contributors, All Rights Reserved.
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

import de.carne.boot.check.Nullable;

/**
 * Custom control displaying the current VM heap usage.
 */
@SuppressWarnings("squid:MaximumInheritanceDepth")
public class HeapInfo extends Canvas implements PaintListener {

	private int timer = 0;

	/**
	 * Constructs a new {@linkplain HeapInfo} instance.
	 *
	 * @param parent the control's parent.
	 * @param style the control's style (no styles supported yet).
	 */
	public HeapInfo(Composite parent, int style) {
		super(parent, style);
		addPaintListener(this);
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
		GC gc = new GC(this);
		Point size;

		try {
			size = gc.textExtent("0000B/0000B", SWT.NONE);
			size.x += 2;
			size.y += 2;
		} finally {
			gc.dispose();
		}
		return size;
	}

	private static final char[] MEM_UNITS = { 'B', 'K', 'M', 'G', 'T', 'P', 'E', 'Z', 'Y' };

	@Override
	public void paintControl(@Nullable PaintEvent event) {
		if (event != null) {
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

			Rectangle clientArea = getClientArea();
			int fillWidth = (clientArea.width * usage) / 100;
			Color defaultBackground = event.gc.getBackground();

			event.gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
			event.gc.fillRectangle(0, 0, fillWidth, clientArea.height);
			event.gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
			event.gc.fillRectangle(fillWidth, 0, clientArea.width - fillWidth, clientArea.height);
			event.gc.setBackground(defaultBackground);

			StringBuilder text = new StringBuilder();

			text.append(used).append(MEM_UNITS[usedMemUnitIndex]).append('/').append(total)
					.append(MEM_UNITS[totalMemUnitIndex]);

			event.gc.drawText(text.toString(), 1, 1, SWT.DRAW_TRANSPARENT);
		}
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
