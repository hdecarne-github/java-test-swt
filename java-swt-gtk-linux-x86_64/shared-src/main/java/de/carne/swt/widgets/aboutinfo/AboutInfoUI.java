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
package de.carne.swt.widgets.aboutinfo;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import de.carne.boot.check.Nullable;
import de.carne.swt.graphics.ResourceException;
import de.carne.swt.graphics.ResourceTracker;
import de.carne.swt.layout.GridLayoutBuilder;
import de.carne.swt.layout.RowLayoutBuilder;
import de.carne.swt.widgets.CompositeBuilder;
import de.carne.swt.widgets.ControlBuilder;
import de.carne.swt.widgets.ShellBuilder;
import de.carne.swt.widgets.UserInterface;
import de.carne.util.ManifestInfos;

class AboutInfoUI extends UserInterface<Shell> {

	private final ResourceTracker resources;
	@Nullable
	private final URL logoUrl;
	private final List<URL> copyrightUrls;

	public AboutInfoUI(Shell root, @Nullable URL logoUrl, List<URL> copyrightUrls) {
		super(root);
		this.resources = ResourceTracker.forDevice(root.getDisplay()).forShell(root);
		this.logoUrl = logoUrl;
		this.copyrightUrls = copyrightUrls;
	}

	@Override
	public void open() throws ResourceException {
		Shell root = buildRoot();

		root.pack();
		root.setMinimumSize(root.getSize());
		root.open();
	}

	private Shell buildRoot() throws ResourceException {
		ShellBuilder rootBuilder = new ShellBuilder(root());
		ControlBuilder<Label> logo = rootBuilder.addControlChild(Label.class, SWT.NONE);
		ControlBuilder<Label> title = rootBuilder.addControlChild(Label.class, SWT.NONE);
		ControlBuilder<Label> version = rootBuilder.addControlChild(Label.class, SWT.NONE);
		ControlBuilder<Label> build = rootBuilder.addControlChild(Label.class, SWT.NONE);
		ControlBuilder<Label> separator1 = rootBuilder.addControlChild(Label.class, SWT.HORIZONTAL | SWT.SEPARATOR);
		CompositeBuilder<TabFolder> copyrights = rootBuilder.addCompositeChild(TabFolder.class, SWT.BOTTOM);
		ControlBuilder<Label> separator2 = rootBuilder.addControlChild(Label.class, SWT.HORIZONTAL | SWT.SEPARATOR);
		CompositeBuilder<Composite> buttons = rootBuilder.addCompositeChild(SWT.NO_BACKGROUND);

		rootBuilder.withText(AboutInfoI18N.i18nTitle(ManifestInfos.APPLICATION_NAME));

		URL checkedLogoUrl = this.logoUrl;

		if (checkedLogoUrl != null) {
			logo.get().setImage(this.resources.getImage(checkedLogoUrl));
		}
		title.get().setText(AboutInfoI18N.i18nLabelTitle(ManifestInfos.APPLICATION_NAME));
		version.get().setText(AboutInfoI18N.i18nLabelVersion(ManifestInfos.APPLICATION_VERSION));
		build.get().setText(AboutInfoI18N.i18nLabelBuild(ManifestInfos.APPLICATION_BUILD));
		buildCopyrights(copyrights);
		buildButtons(buttons);

		GridLayoutBuilder.layout(2).margin(0, 0).apply(rootBuilder);
		GridLayoutBuilder.data().span(1, 3).apply(logo);
		GridLayoutBuilder.data(GridData.FILL_HORIZONTAL).apply(title);
		GridLayoutBuilder.data(GridData.FILL_HORIZONTAL).apply(version);
		GridLayoutBuilder.data(GridData.FILL_HORIZONTAL).apply(build);
		GridLayoutBuilder.data(GridData.FILL_HORIZONTAL).span(2, 1).apply(separator1);
		GridLayoutBuilder.data(GridData.FILL_BOTH).span(2, 1).apply(copyrights);
		GridLayoutBuilder.data(GridData.FILL_HORIZONTAL).span(2, 1).apply(separator2);
		GridLayoutBuilder.data(GridData.FILL_HORIZONTAL).align(SWT.END, SWT.CENTER).span(2, 1).apply(buttons);
		return rootBuilder.get();
	}

	private void buildCopyrights(CompositeBuilder<TabFolder> copyrights) throws ResourceException {
		for (URL copyrightUrl : this.copyrightUrls) {
			try (BufferedReader copyrightReader = new BufferedReader(
					new InputStreamReader(copyrightUrl.openStream()))) {
				String copyrightTitle = copyrightReader.readLine();

				if (copyrightTitle == null) {
					throw new EOFException();
				}

				StringBuilder copyrightInfo = new StringBuilder();
				String copyrightInfoLine;

				while ((copyrightInfoLine = copyrightReader.readLine()) != null) {
					if (copyrightInfo.length() > 0) {
						copyrightInfo.append(System.lineSeparator());
					}
					copyrightInfo.append(copyrightInfoLine);
				}

				TabItem copyrightItem = new TabItem(copyrights.get(), SWT.NONE);

				copyrightItem.setText(copyrightTitle);

				ControlBuilder<Link> copyrightLink = copyrights.addControlChild(Link.class, SWT.NONE);

				copyrightItem.setControl(copyrightLink.get());
				copyrightLink.get().setText(copyrightInfo.toString());
			} catch (IOException e) {
				throw new ResourceException("Failed to load copyright info: " + copyrightUrl, e);
			}
		}
	}

	private void buildButtons(CompositeBuilder<Composite> buttons) {
		ControlBuilder<Button> closeButton = buttons.addControlChild(Button.class, SWT.PUSH);

		closeButton.get().setText(AboutInfoI18N.i18nButtonClose());
		closeButton.onSelected(() -> root().close());
		RowLayoutBuilder.layout().fill(true).apply(buttons);
		RowLayoutBuilder.data().apply(closeButton);
	}

}
