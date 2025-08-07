package app.svck.githubuserapp.ui.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun SkeletonListItem() {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .weight(1f)
                    .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
            )
        }
    }
}

@Composable
fun SkeletonList() {
    LazyColumn(
        modifier = Modifier.shimmer(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(10) {
            SkeletonListItem()
        }
    }
}

@Composable
fun SkeletonUserDetails() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .shimmer(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Placeholder for Name
        Box(
            modifier = Modifier
                .height(28.dp)
                .fillMaxWidth(0.5f)
                .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Placeholder for Login
        Box(
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth(0.3f)
                .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Placeholders for InfoRows
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SkeletonInfoRow()
            SkeletonInfoRow()
            SkeletonInfoRow()
        }
    }
}

@Composable
private fun SkeletonInfoRow() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(Color.LightGray, shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Box(
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth(0.7f)
                .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
        )
    }
}