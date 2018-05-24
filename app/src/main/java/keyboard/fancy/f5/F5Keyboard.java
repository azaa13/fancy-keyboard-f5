package keyboard.fancy.f5;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

public class F5Keyboard extends InputMethodService implements KeyboardView.OnKeyboardActionListener{
//    public F5Keyboard() {
//    }
    private KeyboardView kview;
    private Keyboard keyboard;

    private boolean isCaps=false;

    @Override
    public View onCreateInputView() {
        kview = (KeyboardView)getLayoutInflater().inflate(R.layout.keyboard_layout,null);
        Log.d("kview", "yes");
        keyboard = new Keyboard(this,R.xml.qwerty);
        kview.setOnKeyboardActionListener(this);
        kview.setKeyboard(keyboard);
        return kview;
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection inputConnection = getCurrentInputConnection();
        playclick(primaryCode);
        switch (primaryCode)
        {
            case Keyboard.KEYCODE_DELETE:
                inputConnection.deleteSurroundingText(1,0);
            break;
            case Keyboard.KEYCODE_SHIFT:
                isCaps=!isCaps;
                keyboard.setShifted(isCaps);
                kview.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                inputConnection.sendKeyEvent(new KeyEvent (KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
            break;
            default:
                char code = (char) primaryCode;
                if(Character.isLetter(code) && isCaps)
                    code=Character.toUpperCase(code);
                inputConnection.commitText(String.valueOf(code),1);
        }
    }

    private void playclick(int primaryCode) {
        AudioManager am = (AudioManager)getSystemService(AUDIO_SERVICE);
        switch (primaryCode)
        {
            case 32:
                    am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                    break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;

            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default:am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
