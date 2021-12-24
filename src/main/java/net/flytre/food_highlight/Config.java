package net.flytre.food_highlight;

import com.google.gson.annotations.SerializedName;
import net.flytre.flytre_lib.api.config.ConfigColor;
import net.flytre.flytre_lib.api.config.ConfigHandler;
import net.flytre.flytre_lib.api.config.annotation.DisplayName;

public class Config {

    public static ConfigHandler<Config> HANDLER = new ConfigHandler<>(new Config(), "food_highlight", "config.food_highlight");


    @SerializedName("full_color")
    @DisplayName("Full Hunger and Saturation Color")
    public ConfigColor fullColor = new ConfigColor(0x4D2ab320);

    @SerializedName("partial_color")
    @DisplayName("Full Hunger or Saturation Color")
    public ConfigColor partialColor = new ConfigColor(0x4Dbdb109);


    @SerializedName("render_partial_color")
    @DisplayName("Render for \"Full Hunger or Saturation\"")
    public boolean renderPartialColor = true;

}
