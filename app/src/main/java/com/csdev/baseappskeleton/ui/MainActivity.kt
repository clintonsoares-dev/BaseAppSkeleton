package com.csdev.baseappskeleton.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.csdev.baseappskeleton.ui.screens.greeting.GreetingScreen
import com.csdev.baseappskeleton.ui.theme.BaseAppSkeletonTheme

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
//    private var baseApplication: BaseApplication? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BaseAppSkeletonTheme {
                // navigation control
                navController = rememberNavController()
                val route = remember(navController) { Route(navController) }

                NavHost(
                    navController = navController,
                    startDestination = "${Destination.GREETING}?${DestinationArgs.NAME}=Clinton"
                ) {
                    composable(
                        "${Destination.GREETING}/{${DestinationArgs.NAME}}",
                        arguments = listOf(
                            navArgument(DestinationArgs.NAME) {
                                type = NavType.StringType
                            }
                        )
                    ) {backStackEntry ->
                        GreetingScreen(
                            name = backStackEntry.arguments
                                ?.getString(DestinationArgs.NAME)
                                ?: ""
                        )
                    }
                }
            }
        }
    }
}

/*
For use-cases (club them in a module by types => eg: auth/customer/admin) (example)
-------------
AuthUseCaseModule
    @Module
    @InstallIn(ViewModelComponent::class)
    abstract class AuthUseCaseModuleBinder {
        @Binds
        @ViewModelScoped
        abstract fun bindVerifyAuthUseCase(implementation: VerifyAuthUseCaseImpl): VerifyAuthUseCase
    }

For repos (example)
-------------
RepositoryModule
    @Module
    @InstallIn(ViewModelComponent::class)
    abstract class RepositoryModule {
        @Binds
        @ViewModelScoped
        abstract fun bindAuthenticationRepository(implementation: AuthRepositoryImpl): AuthRepository
    }

For data source (example)
-------------
DataSourceModule
    @Module
    @InstallIn(ViewModelComponent::class)
    abstract class DataSourceModule {

        @Binds
        @ViewModelScoped
        abstract fun bindAuthenticationRemoteDataSource(
            implementation: AuthRemoteDataSourceImpl
        ): AuthRemoteDataSource
    }

*/