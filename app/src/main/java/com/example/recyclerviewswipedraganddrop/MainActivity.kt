package com.example.recyclerviewswipedraganddrop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewswipedraganddrop.adapters.RvAdapter
import com.example.recyclerviewswipedraganddrop.databinding.ActivityMainBinding
import com.example.recyclerviewswipedraganddrop.models.User

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RvAdapter
    private lateinit var userList: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userList = ArrayList()
        for (i in 0..100) {
            userList.add(User("Item: $i", "Phone number: $i"))
        }

        adapter = RvAdapter(userList)
        binding.recyclerView.adapter = adapter

        val itemTouch = object : ItemTouchHelper.Callback() {
            /**
             * Should return a composite flag which defines the enabled move directions in each state
             * (idle, swiping, dragging).
             *
             *
             * Instead of composing this flag manually, you can use [.makeMovementFlags]
             * or [.makeFlag].
             *
             *
             * This flag is composed of 3 sets of 8 bits, where first 8 bits are for IDLE state, next
             * 8 bits are for SWIPE state and third 8 bits are for DRAG state.
             * Each 8 bit sections can be constructed by simply OR'ing direction flags defined in
             * [ItemTouchHelper].
             *
             *
             * For example, if you want it to allow swiping LEFT and RIGHT but only allow starting to
             * swipe by swiping RIGHT, you can return:
             * <pre>
             * makeFlag(ACTION_STATE_IDLE, RIGHT) | makeFlag(ACTION_STATE_SWIPE, LEFT | RIGHT);
            </pre> *
             * This means, allow right movement while IDLE and allow right and left movement while
             * swiping.
             *
             * @param recyclerView The RecyclerView to which ItemTouchHelper is attached.
             * @param viewHolder   The ViewHolder for which the movement information is necessary.
             * @return flags specifying which movements are allowed on this ViewHolder.
             * @see .makeMovementFlags
             * @see .makeFlag
             */
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
                return makeMovementFlags(dragFlags, swipeFlags)
            }

            /**
             * Called when ItemTouchHelper wants to move the dragged item from its old position to
             * the new position.
             *
             *
             * If this method returns true, ItemTouchHelper assumes `viewHolder` has been moved
             * to the adapter position of `target` ViewHolder
             * ([ ViewHolder#getAdapterPosition()][ViewHolder.getAdapterPosition]).
             *
             *
             * If you don't support drag & drop, this method will never be called.
             *
             * @param recyclerView The RecyclerView to which ItemTouchHelper is attached to.
             * @param viewHolder   The ViewHolder which is being dragged by the user.
             * @param target       The ViewHolder over which the currently active item is being
             * dragged.
             * @return True if the `viewHolder` has been moved to the adapter position of
             * `target`.
             * @see .onMoved
             */
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                adapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }

            /**
             * Called when a ViewHolder is swiped by the user.
             *
             *
             * If you are returning relative directions ([.START] , [.END]) from the
             * [.getMovementFlags] method, this method
             * will also use relative directions. Otherwise, it will use absolute directions.
             *
             *
             * If you don't support swiping, this method will never be called.
             *
             *
             * ItemTouchHelper will keep a reference to the View until it is detached from
             * RecyclerView.
             * As soon as it is detached, ItemTouchHelper will call
             * [.clearView].
             *
             * @param viewHolder The ViewHolder which has been swiped by the user.
             * @param direction  The direction to which the ViewHolder is swiped. It is one of
             * [.UP], [.DOWN],
             * [.LEFT] or [.RIGHT]. If your
             * [.getMovementFlags]
             * method
             * returned relative flags instead of [.LEFT] / [.RIGHT];
             * `direction` will be relative as well. ([.START] or [                   ][.END]).
             */
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.onItemDismiss(viewHolder.adapterPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouch)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

    }
}