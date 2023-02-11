package com.example.projmanager.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projmanager.Firebase.FirestoreClass
import com.example.projmanager.R
import com.example.projmanager.models.Board
import com.example.projmanager.utils.Constants
import com.projemanag.adapters.TaskListItemsAdapter
import com.projemanag.model.Task

class Tasklist : base_activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasklist)

        var boardDocumentId = ""
        if (intent.hasExtra(Constants.DOCUMENT_ID)) {
            boardDocumentId = intent.getStringExtra(Constants.DOCUMENT_ID)!!
        }

        showPD()
        FirestoreClass().getBoardDetails(this, boardDocumentId)
        // END
    }

    // TODO (Step 6: Create a function to setup action bar.)
    // START
    /**
     * A function to setup action bar
     */
    private fun setupActionBar(title: String) {

        var toolbar=findViewById<Toolbar>(R.id.toollbar)
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
            actionBar.title = title
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }
    // END

    // TODO (Step 7: Create a function to get the result of Board Detail.)
    // START
    /**
     * A function to get the result of Board Detail.
     */
    fun boardDetails(board: Board) {

        hidePD()

        // TODO (Step 8: call the setup actionbar function.)
        // START
        // Call the function to setup action bar.
        setupActionBar(board.name)
        val addTaskList = Task(resources.getString(R.string.add_list))
        board.taskList.add(addTaskList)

        var rv_task_list=findViewById<RecyclerView>(R.id.rv_task_list)
        rv_task_list?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_task_list.setHasFixedSize(true)

        // Create an instance of TaskListItemsAdapter and pass the task list to it.
        val adapter = TaskListItemsAdapter(this, board.taskList)
        rv_task_list.adapter = adapter // Attach the adapter to the recyclerView.
        // END
    }
}