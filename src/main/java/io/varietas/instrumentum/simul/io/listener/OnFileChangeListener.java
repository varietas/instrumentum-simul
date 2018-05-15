/*
 * The MIT License (MIT)
 * Copyright (c) 2015, Hindol Adhya
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.varietas.instrumentum.simul.io.listener;

/**
 * <h2>OnFileChangeListener</h2>
 * <p>
 * Interface definition for a callback to be invoked when a file under watch is changed.
 *
 * @author Hindol Adhya
 * @author Michael Rhöse
 * @version 1.0.0, 10/1/2017
 */
public interface OnFileChangeListener {

    /**
     * Called when the file is created.
     *
     * @param filePath The file path.
     */
    default void onFileCreate(String filePath) {
    }

    /**
     * Called when the file is modified.
     *
     * @param filePath The file path.
     */
    default void onFileModify(String filePath) {
    }

    /**
     * Called when the file is deleted.
     *
     * @param filePath The file path.
     */
    default void onFileDelete(String filePath) {
    }
}
