package mx.edu.utez.bitacora.ui.features.tasks.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import mx.edu.utez.bitacora.R
import mx.edu.utez.bitacora.ui.components.SelectableButton

enum class FilterOption(@StringRes val labelResId: Int){
    Total(R.string.filter_total),
    Pending(R.string.filter_pending),
    InRevision(R.string.filter_in_revision),
    InProgress(R.string.filter_in_progress),
    Completed(R.string.filter_completed),
    Rejected(R.string.filter_rejected)
}

@Composable
fun FilterLazyRow() {
    var selectedFilter by remember { mutableStateOf(FilterOption.Total) }
    val filters = FilterOption.entries.toTypedArray()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(filters){ filter ->
            SelectableButton(
                text = stringResource(id = filter.labelResId),
                option = filter,
                selectedOption = selectedFilter,
                onClick = { newSelection ->
                    selectedFilter = newSelection
                }
            )
        }
    }
}