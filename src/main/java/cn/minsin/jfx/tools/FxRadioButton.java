package cn.minsin.jfx.tools;

import com.alibaba.fastjson.JSON;
import javafx.collections.ObservableList;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

import java.util.Optional;

/**
 * @author: minton.zhang
 * @since: 2020/4/12 0:57
 */
public class FxRadioButton {

    public static ToggleGroup create() {
        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            ObservableList<Toggle> toggles = toggleGroup.getToggles();
            toggles.remove(new_toggle);
            toggles.forEach(e -> e.setSelected(false));
        });
        return toggleGroup;
    }

    public static <T> T getUserDataValue(ToggleGroup toggleGroup, Class<T> userDataClass) {
        Object userData = toggleGroup.getSelectedToggle().getUserData();
        return JSON.parseObject(userData.toString(), userDataClass);
    }

    public static void setRadioButtonSelect(ToggleGroup toggleGroup, Object userData) {
        ObservableList<Toggle> toggles = toggleGroup.getToggles();
        Optional<Toggle> first = toggles.stream().filter(e -> e.getUserData().equals(userData.toString())).findFirst();
        first.ifPresent(toggle -> toggle.setSelected(true));
    }
}
