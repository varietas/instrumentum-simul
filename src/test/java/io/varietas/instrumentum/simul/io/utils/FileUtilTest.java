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

import io.varietas.instrumentum.simul.io.utils.FileUtil;
import io.varietas.instrumentum.simul.TestConstants;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 *
 * @author Michael Rhöse
 */
@RunWith(JUnit4.class)
public class FileUtilTest {

    private static final Path PATH = Paths.get(TestConstants.TEST_FOLDER_PATH);

    /**
     * Test of matcherForGlobExpression method, of class FileUtil.
     */
    @Test
    public void testMatcherForGlobExpressionTrue() {

        PathMatcher result = FileUtil.matcherForGlobExpression(TestConstants.TEST_FOLDER_PATH);
        Assertions.assertThat(FileUtil.matches(PATH, result)).isTrue();
    }

    /**
     * Test of matcherForGlobExpression method, of class FileUtil.
     */
    @Test
    public void testMatcherForGlobExpressionFalse() {

        PathMatcher result = FileUtil.matcherForGlobExpression("/temp");
        Assertions.assertThat(FileUtil.matches(PATH, result)).isFalse();
    }

    /**
     * Test of matches method, of class FileUtil.
     */
    @Test
    public void testMatches() {

        PathMatcher result = FileUtil.matcherForGlobExpression(TestConstants.TEST_FOLDER_PATH);
        Assertions.assertThat(FileUtil.matches(PATH, result)).isTrue();

        result = FileUtil.matcherForGlobExpression("/temp");
        Assertions.assertThat(FileUtil.matches(PATH, result)).isFalse();
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

        Assertions.assertThat(FileUtil.matchesAny(PATH, patterns)).isFalse();

        patterns.add(FileUtil.matcherForGlobExpression(TestConstants.TEST_FOLDER_PATH));

        Assertions.assertThat(FileUtil.matchesAny(PATH, patterns)).isTrue();
    }
}
