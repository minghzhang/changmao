package com.louis.audio;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @date : 2021/9/24
 */
public class Demo {
    String filePath = "/Users/landon.zhang/Documents/SourceCode/audio/voice_2.wav";

    private static volatile boolean stopped = false;

    //录制音频
    public void getAudio_A() {//提供wave格式信息
        try {
            AudioFormat format = new AudioFormat(22050, 16, 1, true, false);
            TargetDataLine targetDataLine = AudioSystem.getTargetDataLine(format);
            targetDataLine.open(format);
            targetDataLine.start();
            System.out.println("录音中");
            //直接播放出来
            SourceDataLine sourceDataLine = AudioSystem.getSourceDataLine(format);
            sourceDataLine.open(format);
            sourceDataLine.start();
            System.out.println("播放中");

            //开子线程进行播放
            byte[] b = new byte[1024];//缓存音频数据
            new Thread(new Runnable() {
                public void run() {
                    int a = 0;
                    while (!stopped && a != -1) {
                        System.out.println("录制中");
                        a = targetDataLine.read(b, 0, b.length);//捕获录音数据
                        if (a != -1) {
                            sourceDataLine.write(b, 0, a);//播放录制的声音
                        }
                    }
                }
            }).start();

            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    stopped = true;

                    // AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File(filePath));


                }
            }.start();

        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Demo().getAudio_A();
    }
}
