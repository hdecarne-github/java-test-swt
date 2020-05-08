/*
 * Copyright (c) 2017-2020 Holger de Carne and contributors, All Rights Reserved.
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
package de.carne.test.swt.test.tester;

import java.util.Objects;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.ToolBar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.carne.nio.file.FileUtil;
import de.carne.test.swt.DisableIfThreadNotSWTCapable;
import de.carne.test.swt.tester.SWTTest;
import de.carne.test.swt.tester.accessor.ButtonAccessor;
import de.carne.test.swt.tester.accessor.CompositeAccessor;
import de.carne.test.swt.tester.accessor.ControlAccessor;
import de.carne.test.swt.tester.accessor.CoolBarAccessor;
import de.carne.test.swt.tester.accessor.ToolBarAccessor;

/**
 * Test {@linkplain SWTTest} class - Full application test.
 */
@DisableIfThreadNotSWTCapable
class SWTTestApplicationTest extends SWTTest {

	@Test
	void testApplication() {
		Script script = script(SWTTestApplication::main);

		script.add(this::doTestMessage);
		script.add(this::doTestColorDialog);
		script.add(this::doTestDirectoryDialog);
		script.add(this::doTestFileDialog);
		script.add(this::doTestFontDialog);
		script.add(this::doTestPrintDialog);
		script.add(this::doTestMiddleButton);
		script.add(this::doTestMiddleCoolButton);
		script.add(this::doCloseRoot);
		script.execute();

		Assertions.assertTrue(script.passed());
	}

	protected void doTestMessage() {
		traceAction();

		int result = SWT.OK;

		mockMessageBox().result(result);

		accessShell().accessChild(ToolBarAccessor::new, ToolBar.class, 0).accessItem(0).select();

		Assertions.assertEquals("MessageBox: " + result, getLastMessage());
	}

	protected void doTestColorDialog() {
		traceAction();

		RGB color = new RGB(1, 2, 3);

		mockColorDialog().result(color);

		accessShell().accessChild(ToolBarAccessor::new, ToolBar.class, 0).accessItem(SWTTestApplication.TOOL_ITEM_COLOR)
				.select();

		Assertions.assertEquals("ColorDialog: " + color, getLastMessage());
	}

	protected void doTestDirectoryDialog() {
		traceAction();

		String directory = FileUtil.workingDir().toString();

		mockDirectoryDialog().result(directory);

		accessShell().accessChild(ToolBarAccessor::new, ToolBar.class, 0)
				.accessItem(SWTTestApplication.TOOL_ITEM_DIRECTORY).select();

		Assertions.assertEquals("DirectoryDialog: " + directory, getLastMessage());
	}

	protected void doTestFileDialog() {
		traceAction();

		String file = FileUtil.tmpDir().resolve("afile.txt").toString();

		mockFileDialog().result(file);

		accessShell().accessChild(ToolBarAccessor::new, ToolBar.class, 0).accessItem(SWTTestApplication.TOOL_ITEM_FILE)
				.select();

		Assertions.assertEquals("FileDialog: " + file, getLastMessage());
	}

	protected void doTestFontDialog() {
		traceAction();

		FontData font = Objects.requireNonNull(Display.getCurrent().getFontList(null, true)[0]);

		mockFontDialog().result(font);

		accessShell().accessChild(ToolBarAccessor::new, ToolBar.class, 0).accessItem(SWTTestApplication.TOOL_ITEM_FONT)
				.select();

		Assertions.assertEquals("FontDialog: " + font, getLastMessage());
	}

	protected void doTestPrintDialog() {
		traceAction();

		PrinterData printer = new PrinterData("MockDriver", "MockPrinter");

		mockPrintDialog().result(printer);

		accessShell().accessChild(ToolBarAccessor::new, ToolBar.class, 0).accessItem(SWTTestApplication.TOOL_ITEM_PRINT)
				.select();

		Assertions.assertEquals("PrintDialog: " + printer, getLastMessage());
	}

	protected void doTestMiddleButton() {
		traceAction();

		accessShell().accessChild(CompositeAccessor::new, Composite.class, 1)
				.accessButton(ButtonAccessor.matchText(SWTTestApplication.BUTTON_MIDDLE)).select();

		Assertions.assertEquals("Button selected: " + SWTTestApplication.BUTTON_MIDDLE, getLastMessage());
	}

	protected void doTestMiddleCoolButton() {
		traceAction();

		accessShell().accessChild(CoolBarAccessor::new, CoolBar.class, 2).accessItem(1)
				.accessControl(ButtonAccessor::new, Button.class).select();

		Assertions.assertEquals("CoolButton selected: " + SWTTestApplication.BUTTON_MIDDLE, getLastMessage());
	}

	protected void doCloseRoot() {
		traceAction();

		accessShell().get().close();
	}

	private String getLastMessage() {
		List messages = accessShell().accessChild(ControlAccessor::new, List.class, 4).get();

		return messages.getItem(messages.getItemCount() - 1);
	}

}
