# Mace Damage Indicator

A lightweight Spigot/Paper plugin that displays the damage dealt by mace attacks with stylish color-coded indicators!

When you smash something with a mace, this plugin shows exactly how much damage you dealt!!! either in your action bar or as a chat message—with colors that scale based on the damage amount.

---

## Features

- **Real-time damage display** – See exactly how much damage your mace deals
- **Color-coded tiers** – Damage color changes based on your mace dmg
- **Action bar or chat support** – Choose your preferred display method (Actionbar is probably better)
- **Fully customizable** – Pretty much everything is configurable

---

## Installation

1. **Download** the latest `MaceDamageIndicator.jar` from the releases page
2. **Place** the `.jar` file into your server's `plugins/` folder
3. **Restart** your server (or use `/reload` if you're feeling brave)
4. **Configure** the plugin by editing `plugins/MaceDamageIndicator/config.yml`
5. **Reload** the config with `/macedamageindicator reload` for a hot reload (or restart the server again i guess)

---

## Configuration

All settings are stored in `plugins/MaceDamageIndicator/config.yml`. Here's what each option does:

### Core Settings

| Setting              | Description                                                                            | Possible Values                   | Default     |
|:---------------------|:---------------------------------------------------------------------------------------|:----------------------------------|:------------|
| `enabled`            | Master toggle for the plugin                                                           | `true` / `false`                  | `true`      |
| `display-mode`       | Where the damage indicator appears                                                     | `actionbar` or `text`             | `actionbar` |
| `min-mace-damage`    | Minimum damage required for the indicator to show (Prevents spam from teeny tiny hits) | Any positive number               | `6.0`       |
| `indicator-decimals` | How many decimal places to display                                                     | Any integer (e.g., `0`, `1`, `2`) | `2`         |

### Message Formatting

| Setting             | Description                                                                                           | Default                               |
|:--------------------|:------------------------------------------------------------------------------------------------------|:--------------------------------------|
| `indicator-message` | The message template shown when damage is dealt. Use `{damage}` as a placeholder for the damage value | `"§b§lᴍᴀᴄᴇ ᴅᴀᴍᴀɢᴇ §r§7>> §r{damage}"` |

**Color formatting:** Use `§` followed by a color code (`§a` = green, `§c` = red, etc. etc.)

[Minecraft Color Codes](https://htmlcolorcodes.com/minecraft-color-codes/ "Click to see all color codes!")

### Damage Color Tiers

The plugin checks these tiers **in order from top to bottom**. The first tier where the damage is **less than** `max` will be used.

```yaml
damage-tiers:
  - max: 15
    color: GREEN     # Damage < 15 → Green
  - max: 20
    color: YELLOW    # 15 ≤ Damage < 20 → Yellow
  - max: 30
    color: RED       # 20 ≤ Damage < 30 → Red

default-color: DARK_RED # What color to show if the damage is more than 30?
