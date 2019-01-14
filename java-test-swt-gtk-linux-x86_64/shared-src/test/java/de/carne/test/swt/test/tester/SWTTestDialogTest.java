/*
 * Copyright (c) 2017-2019 Holger de Carne and contributors, All Rights Reserved.
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

import java.nio.file.Paths;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.carne.test.swt.DisableIfThreadNotSWTCapable;
import de.carne.test.swt.app.TestAppMain;
import de.carne.test.swt.app.TestAppTest;
import de.carne.test.swt.tester.SWTTest;

/**
 * Test {@linkplain SWTTest} class - Native dialog mocks.
 */
@DisableIfThreadNotSWTCapable
class SWTTestDialogTest extends TestAppTest {

	@Test
	void testStartStop() {
		Script script = script(new TestAppMain()).args(getClass().getSimpleName());

		script.add(this::doOpenMessageBox);
		script.add(this::doFileDialog);
		script.add(this::doDirectoryDialog);
		script.add(this::doPrintDialog);
		script.add(this::doColorDialog);
		script.add(this::doFontDialog);
		script.add(this::doClose).execute();
	}

	private void doOpenMessageBox() {
		traceAction();

		mockMessageBox().result(SWT.NO);

		MessageBox messageBox = new MessageBox(accessShell().get(), SWT.ICON_QUESTION | SWT.YES | SWT.NO);

		messageBox.setText(getClass().getSimpleName());
		messageBox.setMessage("Guess the answer for this test?");

		Assertions.assertEquals(SWT.NO, messageBox.open());
		Assertions.assertEquals(SWT.CANCEL, messageBox.open());
	}

	private void doFileDialog() {
		traceAction();

		String testFileName = getClass().getSimpleName() + ".java";

		mockFileDialog().result(testFileName);

		FileDialog fileDialog = new FileDialog(accessShell().get(), SWT.OPEN);

		Assertions.assertEquals(testFileName, fileDialog.open());
		Assertions.assertNull(fileDialog.open());
	}

	private void doDirectoryDialog() {
		traceAction();

		String testDirectoryName = Paths.get(".").toAbsolutePath().toString();

		mockDirectoryDialog().result(testDirectoryName);

		DirectoryDialog directoryDialog = new DirectoryDialog(accessShell().get(), SWT.OPEN);

		Assertions.assertEquals(testDirectoryName, directoryDialog.open());
		Assertions.assertNull(directoryDialog.open());
	}

	private void doPrintDialog() {
		traceAction();

		PrinterData testPrinterData = new PrinterData("generic", "dummy");

		mockPrintDialog().result(testPrinterData);

		PrintDialog printDialog = new PrintDialog(accessShell().get());
		PrinterData printDialogResult = printDialog.open();

		Assertions.assertNotNull(printDialogResult);
		Assertions.assertEquals(testPrinterData.toString(), printDialogResult.toString());
		Assertions.assertNull(printDialog.open());
	}

	private void doColorDialog() {
		traceAction();

		RGB testRGB = new RGB(1, 2, 3);

		mockColorDialog().result(testRGB);

		ColorDialog colorDialog = new ColorDialog(accessShell().get());
		RGB colorDialogResult = colorDialog.open();

		Assertions.assertEquals(testRGB, colorDialogResult);
		Assertions.assertNull(colorDialog.open());
	}

	private void doFontDialog() {
		traceAction();

		Shell shell = accessShell().get();
		Display display = shell.getDisplay();
		FontData testFontData = display.getSystemFont().getFontData()[0];

		mockFontDialog().result(testFontData);

		FontDialog fontDialog = new FontDialog(shell);
		FontData fontDialogResult = fontDialog.open();

		Assertions.assertNotNull(fontDialogResult);
		Assertions.assertEquals(testFontData, fontDialogResult);
		Assertions.assertNull(fontDialog.open());
	}

}
