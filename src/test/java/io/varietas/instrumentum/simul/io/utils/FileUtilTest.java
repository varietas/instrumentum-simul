/*
 * Copyright 2018 Michael Rhöse.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.varietas.instrumentum.simul.io.utils;

import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.HashSet;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 *
 * @author Michael Rhöse
 */
public class FileUtilTest {

    @TempDir
    public static Path FOLDER;

    /**
     * Test of matcherForGlobExpression method, of class FileUtil.
     */
    @Test
    public void testMatcherForGlobExpressionTrue() {

        PathMatcher result = FileUtil.matcherForGlobExpression(FOLDER.toFile().getPath());
        Assertions.assertThat(FileUtil.matches(FOLDER, result)).isTrue();
    }

    /**
     * Test of matcherForGlobExpression method, of class FileUtil.
     */
    @Test
    public void testMatcherForGlobExpressionFalse() {

        PathMatcher result = FileUtil.matcherForGlobExpression("/temp");
        Assertions.assertThat(FileUtil.matches(FOLDER, result)).isFalse();
    }

    /**
     * Test of matches method, of class FileUtil.
     */
    @Test
    public void testMatches() {

        PathMatcher result = FileUtil.matcherForGlobExpression(FOLDER.toString());
        Assertions.assertThat(FileUtil.matches(FOLDER, result)).isTrue();

        result = FileUtil.matcherForGlobExpression("/temp");
        Assertions.assertThat(FileUtil.matches(FOLDER, result)).isFalse();
    }

    /**
     * Test of matchesAny method, of class FileUtil.
     */
    @Test
    public void testMatchesAny() {

        Set<PathMatcher> patterns = new HashSet<PathMatcher>() {
            {
                add(FileUtil.matcherForGlobExpression("/notADir1"));
                add(FileUtil.matcherForGlobExpression("/notADir2"));
            }
        };

        Assertions.assertThat(FileUtil.matchesAny(FOLDER, patterns)).isFalse();

        patterns.add(FileUtil.matcherForGlobExpression(FOLDER.toString()));

        Assertions.assertThat(FileUtil.matchesAny(FOLDER, patterns)).isTrue();
    }
}
