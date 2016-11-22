package com.rbkmoney.dudoser.utils;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static com.rbkmoney.dudoser.utils.FileHelper.FILENAME_LAST_EVENT_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileHelperTest {

    @Before
    public void setUp() {
        FileHelper.pathToFolder = "c:\\work\\";
    }

    @Test
    public void testGetLastEventId() throws IOException {
        File file = FileHelper.getFile(FILENAME_LAST_EVENT_ID);
        assertTrue(file.exists());

        for (int i = 0; i < 10; i++) {
            String count = String.valueOf(i);
            FileHelper.writeFile(file.getAbsolutePath(), count);

            assertEquals(count, FileHelper.readFile(file.getAbsolutePath()));
            assertEquals(count, FileHelper.getLastEventId());
        }
        file.delete();
    }

    @Test
    public void testGetFile() throws IOException {
        File fileUtils = FileHelper.getFile(FILENAME_LAST_EVENT_ID);
        assertTrue(fileUtils.exists());

        File file = new File(FileHelper.pathToFolder + FILENAME_LAST_EVENT_ID);
        assertTrue(file.exists());

        assertEquals(fileUtils.getAbsolutePath(), file.getAbsolutePath());
        assertEquals(fileUtils.canWrite(), file.canWrite());
        assertEquals(fileUtils.canRead(), file.canRead());
        fileUtils.delete();
        file.delete();
    }

    @Test
    public void testReadAndWriteFile() throws IOException {
        File file = FileHelper.getFile(FILENAME_LAST_EVENT_ID);
        assertTrue(file.exists());
        assertTrue(file.canRead());
        assertTrue(file.canWrite());

        FileHelper.writeFile(file.getAbsolutePath(), "123");

        assertEquals("123", FileHelper.readFile(file.getAbsolutePath()));
        file.delete();
    }

}
