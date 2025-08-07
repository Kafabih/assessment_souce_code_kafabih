package app.svck.githubuserapp



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.svck.githubuserapp.ui.presentation.components.NotificationPermissionHandler
import app.svck.githubuserapp.ui.presentation.pages.UserDetailsScreen
import app.svck.githubuserapp.ui.presentation.pages.UserSearchScreen
import app.svck.githubuserapp.ui.theme.GithubUserAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubUserAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Wrap the app's content with the permission handler
                    NotificationPermissionHandler {
                        val navController = rememberNavController()
                        NavHost(navController = navController, startDestination = "userSearch") {
                            composable("userSearch") {
                                UserSearchScreen(navController)
                            }
                            composable(
                                route = "userDetails/{username}",
                                arguments = listOf(navArgument("username") { type = NavType.StringType })
                            ) { backStackEntry ->
                                UserDetailsScreen(
                                    username = backStackEntry.arguments?.getString("username") ?: "",
                                    navController = navController
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}