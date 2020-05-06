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

import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import de.carne.util.Late;

class SWTTestApplication {

	final static String ROOT_TITLE = "root";

	final static String TOOL_ITEM_MESSAGE = "Message";
	final static String TOOL_ITEM_COLOR = "Color";
	final static String TOOL_ITEM_DIRECTORY = "Directory";
	final static String TOOL_ITEM_FILE = "File";
	final static String TOOL_ITEM_FONT = "Font";
	final static String TOOL_ITEM_PRINT = "Print";

	private final Shell root;
	private final Late<List> messageListHolder = new Late<>();

	private SWTTestApplication(Shell root) {
		this.root = root;
	}

	public static void main(String[] args) {
		Display display = new Display();
		Shell root = new Shell(display);

		new SWTTestApplication(root).setupAndRun(args);
	}

	private void setupAndRun(String[] args) {
		setupRoot();
		this.root.open();

		addMessage("Command line: " + Arrays.toString(args));

		Display display = this.root.getDisplay();

		while (!this.root.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void setupRoot() {
		this.root.setText(ROOT_TITLE);
		this.root.setLayout(new GridLayout(1, true));
		this.root.addShellListener(new ShellListener() {

			@Override
			public void shellIconified(ShellEvent evt) {
				addMessage("shellIconified");
			}

			@Override
			public void shellDeiconified(ShellEvent evt) {
				addMessage("shellDeiconified");
			}

			@Override
			public void shellDeactivated(ShellEvent evt) {
				addMessage("shellDeactivated");
			}

			@Override
			public void shellClosed(ShellEvent evt) {
				addMessage("shellClosed");
			}

			@Override
			public void shellActivated(ShellEvent evt) {
				addMessage("shellActivated");
			}

		});

		ToolBar dialogBar = new ToolBar(this.root, SWT.FLAT);

		dialogBar.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		setupDialogBar(dialogBar);

		Label messageListLabel = new Label(this.root, SWT.LEFT);

		messageListLabel.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		messageListLabel.setText("Messages");

		List messageList = new List(this.root, SWT.SINGLE);

		messageList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.messageListHolder.set(messageList);
	}

	private void setupDialogBar(ToolBar dialogBar) {
		ToolItem messageItem = new ToolItem(dialogBar, SWT.PUSH);

		messageItem.setText(TOOL_ITEM_MESSAGE);
		messageItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent evt) {
				onMessageBoxSelection();
			}

		});

		ToolItem colorItem = new ToolItem(dialogBar, SWT.PUSH);

		colorItem.setText(TOOL_ITEM_COLOR);
		colorItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent evt) {
				onColorDialogSelection();
			}

		});

		ToolItem directoryItem = new ToolItem(dialogBar, SWT.PUSH);

		directoryItem.setText(TOOL_ITEM_DIRECTORY);
		directoryItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent evt) {
				onDirectoryDialogSelection();
			}

		});

		ToolItem fileItem = new ToolItem(dialogBar, SWT.PUSH);

		fileItem.setText(TOOL_ITEM_FILE);
		fileItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent evt) {
				onFileDialogSelection();
			}

		});

		ToolItem fontItem = new ToolItem(dialogBar, SWT.PUSH);

		fontItem.setText(TOOL_ITEM_FONT);
		fontItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent evt) {
				onFontDialogSelection();
			}

		});

		ToolItem printItem = new ToolItem(dialogBar, SWT.PUSH);

		printItem.setText(TOOL_ITEM_PRINT);
		printItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent evt) {
				onPrintDialogSelection();
			}

		});
	}

	private void onMessageBoxSelection() {
		MessageBox message = new MessageBox(this.root, SWT.ICON_INFORMATION | SWT.OK);

		message.setText("Title");
		message.setMessage("Message");
		int status = message.open();

		addMessage("MessageBox: " + status);
	}

	private void onColorDialogSelection() {
		ColorDialog dialog = new ColorDialog(this.root, SWT.DEFAULT);

		RGB color = dialog.open();

		addMessage("ColorDialog: " + color);
	}

	private void onDirectoryDialogSelection() {
		DirectoryDialog dialog = new DirectoryDialog(this.root, SWT.DEFAULT);

		String directory = dialog.open();

		addMessage("DirectoryDialog: " + directory);
	}

	private void onFileDialogSelection() {
		FileDialog dialog = new FileDialog(this.root, SWT.OPEN);

		String file = dialog.open();

		addMessage("FileDialog: " + file);
	}

	private void onFontDialogSelection() {
		FontDialog dialog = new FontDialog(this.root, SWT.DEFAULT);

		FontData font = dialog.open();

		addMessage("FontDialog: " + font);
	}

	private void onPrintDialogSelection() {
		PrintDialog dialog = new PrintDialog(this.root, SWT.DEFAULT);

		PrinterData printer = dialog.open();

		addMessage("PrintDialog: " + printer);
	}

	private void addMessage(String message) {
		List messageList = this.messageListHolder.get();

		messageList.add(message);
		messageList.select(messageList.getItemCount() - 1);
	}

}
