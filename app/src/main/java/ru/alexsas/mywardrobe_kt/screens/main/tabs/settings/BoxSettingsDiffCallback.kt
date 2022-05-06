package ua.cn.stu.navcomponent.tabs.screens.main.tabs.settings

import androidx.recyclerview.widget.DiffUtil
import ru.alexsas.mywardrobe_kt.screens.main.tabs.settings.BoxSetting

class BoxSettingsDiffCallback(
    private val oldList: List<BoxSetting>,
    private val newList: List<BoxSetting>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].box.id == newList[newItemPosition].box.id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}