package listener;

import com.leafclient.buddy.listener.annotation.Subscribe;
import event.SimpleEvent;

public class SimpleListener {

    @Subscribe
    public void onEvent(SimpleEvent e) {
        e.XD += 15;
        e.XD *= 20;
    }

}
