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
package de.carne.swt.layout;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

/**
 * {@linkplain GridLayout} builder.
 */
public final class GridLayoutBuilder extends LayoutBuilder<GridLayout> {

	private GridLayoutBuilder(GridLayout layout) {
		super(layout);
	}

	/**
	 * Start layout build.
	 *
	 * @return The created builder.
	 */
	public static GridLayoutBuilder layout() {
		return new GridLayoutBuilder(new GridLayout());
	}

	/**
	 * Start layout build.
	 *
	 * @param numColumns The number of grid columns.
	 * @return The created builder.
	 */
	public static GridLayoutBuilder layout(int numColumns) {
		return layout(numColumns, false);
	}

	/**
	 * Start layout build.
	 *
	 * @param numColumns The number of grid columns.
	 * @param makeColumnsEqualWidth Whether to make the grid columns equal width.
	 * @return The created builder.
	 * @see GridLayout#GridLayout(int, boolean)
	 */
	public static GridLayoutBuilder layout(int numColumns, boolean makeColumnsEqualWidth) {
		return new GridLayoutBuilder(new GridLayout(numColumns, makeColumnsEqualWidth));
	}

	/**
	 * Set the layout's margin attributes.
	 *
	 * @param width The horizontal margin.
	 * @param height The vertical margin.
	 * @return The updated builder.
	 */
	public GridLayoutBuilder margin(int width, int height) {
		GridLayout layout = get();

		layout.marginWidth = width;
		layout.marginHeight = height;
		return this;
	}

	/**
	 * Set the layout's margin attributes.
	 *
	 * @param left The left margin.
	 * @param top The top margin.
	 * @param right The right margin.
	 * @param bottom The bottom margin.
	 * @return The updated builder.
	 */
	public GridLayoutBuilder margin(int left, int top, int right, int bottom) {
		GridLayout layout = get();

		layout.marginLeft = left;
		layout.marginTop = top;
		layout.marginRight = right;
		layout.marginBottom = bottom;
		return this;
	}

	/**
	 * Set the layout's spacing attributes.
	 *
	 * @param horizontalSpacing The horizontal spacing.
	 * @param verticalSpacing The vertical spacing.
	 * @return The updated builder.
	 */
	public GridLayoutBuilder spacing(int horizontalSpacing, int verticalSpacing) {
		GridLayout layout = get();

		layout.horizontalSpacing = horizontalSpacing;
		layout.verticalSpacing = verticalSpacing;
		return this;
	}

	/**
	 * Begin layout data build.
	 *
	 * @return The created builder.
	 */
	public static DataBuilder data() {
		return new DataBuilder(new GridData());
	}

	/**
	 * {@linkplain GridData} builder.
	 */
	public static class DataBuilder extends LayoutDataBuilder<GridData> {

		DataBuilder(GridData layoutData) {
			super(layoutData);
		}

		/**
		 * Set the layout data's alignment attributes.
		 *
		 * @param horizontalAlignment The horizontal alignment.
		 * @param verticalAlignment The vertical alignment.
		 * @return The updated builder.
		 */
		public DataBuilder align(int horizontalAlignment, int verticalAlignment) {
			GridData layoutData = get();

			layoutData.horizontalAlignment = horizontalAlignment;
			layoutData.verticalAlignment = verticalAlignment;
			return this;
		}

		/**
		 * Set the layout data's preferred size attributes.
		 *
		 * @param widthHint The preferred width in pixels.
		 * @param heightHint The preferred height in pixels.
		 * @return The updated builder.
		 */
		public DataBuilder preferredSize(int widthHint, int heightHint) {
			GridData layoutData = get();

			layoutData.widthHint = widthHint;
			layoutData.heightHint = heightHint;
			return this;
		}

		/**
		 * Set the layout data's minimum size attributes.
		 *
		 * @param minimumWidth The minimum width in pixels.
		 * @param minimumHeight The minimum height in pixels.
		 * @return The updated builder.
		 */
		public DataBuilder minimumSize(int minimumWidth, int minimumHeight) {
			GridData layoutData = get();

			layoutData.minimumWidth = minimumWidth;
			layoutData.minimumHeight = minimumHeight;
			return this;
		}

		/**
		 * Set the layout data's indent attributes.
		 *
		 * @param horizontalIndent The horizontal indent.
		 * @param verticalIndent The vertical indent.
		 * @return The updated builder.
		 */
		public DataBuilder indent(int horizontalIndent, int verticalIndent) {
			GridData layoutData = get();

			layoutData.horizontalIndent = horizontalIndent;
			layoutData.verticalIndent = verticalIndent;
			return this;
		}

		/**
		 * Set the layout data's span attributes.
		 *
		 * @param horizontalSpan The horizontal span.
		 * @param verticalSpan The vertical span.
		 * @return The updated builder.
		 */
		public DataBuilder span(int horizontalSpan, int verticalSpan) {
			GridData layoutData = get();

			layoutData.horizontalSpan = horizontalSpan;
			layoutData.verticalSpan = verticalSpan;
			return this;
		}

		/**
		 * Set the layout data's grab attributes.
		 *
		 * @param grabExcessHorizontalSpace Whether to grap excessive horizontal space.
		 * @param grabExcessVerticalSpace Whether to grap excessive vertical space.
		 * @return The updated builder.
		 */
		public DataBuilder grab(boolean grabExcessHorizontalSpace, boolean grabExcessVerticalSpace) {
			GridData layoutData = get();

			layoutData.grabExcessHorizontalSpace = grabExcessHorizontalSpace;
			layoutData.grabExcessVerticalSpace = grabExcessVerticalSpace;
			return this;
		}

		/**
		 * Set the layout data's exclude attribute.
		 *
		 * @param exclude Whether to exclude the control during layout.
		 * @return The updated builder.
		 */
		public DataBuilder exclude(boolean exclude) {
			get().exclude = exclude;
			return this;
		}

	}

}
