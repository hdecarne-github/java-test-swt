/*
 * Copyright (c) 2007-2019 Holger de Carne and contributors, All Rights Reserved.
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
package de.carne.swt.test.app;

import de.carne.test.swt.tester.SWTTest;
import de.carne.test.swt.tester.accessor.ButtonAccessor;
import de.carne.test.swt.tester.accessor.ItemAccessor;

/**
 * Base class providing the actual {@linkplain TestApp} tests for execution within multiple projects.
 */
@SuppressWarnings("squid:S2187")
public class TestAppTest extends SWTTest {

	protected void executeTestScript() {
		Script script = script(new TestAppMain());

		script.add(this::doOpenAboutinfo, true);
		script.add(this::doCloseAboutinfo);
		script.add(this::doClose);
		script.execute();
	}

	private void doOpenAboutinfo() {
		traceAction();
		accessShell().accessMenuItem(ItemAccessor.matchText(TestApp.ROOT_MENU_WIDGETS_ABOUTINFO)).select();
	}

	private void doCloseAboutinfo() {
		traceAction();
		accessShell("About <undefined>").accessButton(ButtonAccessor.matchText("Close")).select();
	}

	private void doClose() {
		traceAction();
		accessShell().accessMenuItem(ItemAccessor.matchText(TestApp.ROOT_MENU_SHELL_CLOSE)).select();
	}

}
