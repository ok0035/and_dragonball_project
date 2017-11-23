package com.example.mapl0.db_game_project.Framework;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

/**
 * Created by mapl0 on 2016-11-05.
 */

public class SoundManager {

    private static SoundManager s_instance;

    private SoundPool       m_SoundPool;
    private HashMap         m_SoundPoolMap; // 불러온 사운드의 아이디 값을 저장할 해시맵
    private AudioManager    m_AudioManager;
    private Context         m_Activity; // 어플리케이션의 컨텍스트 값

    public void Init(Context _context) {

        // 맴버 변수 생성과 초기화
        m_SoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        m_SoundPoolMap = new HashMap();
        m_AudioManager = (AudioManager)_context.getSystemService(Context.AUDIO_SERVICE);
        m_Activity = _context;
    }

    public void addSound(int _index, int _soundID) {
        int id = m_SoundPool.load(m_Activity, _soundID, 1); // 사운드를 로드
        m_SoundPoolMap.put(_index, id); // 해시맵에 아이디 값을 받아온 인덱스로 저장
    }

    //사운드 재생 코드
    public void play(int _index) {
        float streamVolume = m_AudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        streamVolume = streamVolume / m_AudioManager.
                getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        m_SoundPool.play((Integer) m_SoundPoolMap.get(_index),
                streamVolume, streamVolume, 1, 0, 1f);
    }

    public void playLooped(int _index) {

        float streamVolume = m_AudioManager.getStreamVolume
                (AudioManager.STREAM_MUSIC);

        streamVolume = streamVolume / m_AudioManager.getStreamMaxVolume
            (AudioManager.STREAM_MUSIC);

        m_SoundPool.play((Integer) m_SoundPoolMap.get(_index),
                streamVolume, streamVolume, 1, -1, 1f);
    }


    public static SoundManager getInstance() {
        if(s_instance == null) {
            s_instance = new SoundManager();
        }

        return s_instance;
    }
}
