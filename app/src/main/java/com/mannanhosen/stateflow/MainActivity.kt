package com.mannanhosen.stateflow
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mannanhosen.stateflow.ui.theme.StateflowTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StateflowTheme {
             lifecycleScope.launch {
                 repeatOnLifecycle(Lifecycle.State.STARTED) {
                     val result = producer()
                     result.collect {
                         delay(1000)
                     Log.d("StateFlow", "Item: $it")
                     }
                 }

             }
            }
        }
    }

    private fun producer() : Flow<Int> {
        val mutableStateFlow = MutableStateFlow(10)
        lifecycleScope.launch {
            delay(2000)
            mutableStateFlow.emit(2000)
            delay(2000)
            mutableStateFlow.emit(3000)
        }
      return mutableStateFlow
    }

}

