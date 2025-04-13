package com.compose.hangf_aos.data.repository.di

import com.compose.hangf_aos.data.repository.repoImpl.CustomerRepositoryImpl
import com.compose.hangf_aos.data.repository.repoImpl.MenuOrderRepositoryImpl
import com.compose.hangf_aos.data.repository.repoImpl.MenuRepositoryImpl
import com.compose.hangf_aos.data.repository.repoImpl.OrderRepositoryImpl
import com.compose.hangf_aos.data.repository.repoImpl.OwnerRepositoryImpl
import com.compose.hangf_aos.data.repository.repoImpl.StoreRepositoryImpl
import com.compose.hangf_aos.data.repository.repoInterfaces.CustomerRepository
import com.compose.hangf_aos.data.repository.repoInterfaces.MenuOrderRepository
import com.compose.hangf_aos.data.repository.repoInterfaces.MenuRepository
import com.compose.hangf_aos.data.repository.repoInterfaces.OrderRepository
import com.compose.hangf_aos.data.repository.repoInterfaces.OwnerRepository
import com.compose.hangf_aos.data.repository.repoInterfaces.StoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCustomerRepository(
        impl: CustomerRepositoryImpl
    ): CustomerRepository

    @Binds
    abstract fun bindMenuRepository(
        impl: MenuRepositoryImpl
    ): MenuRepository

    @Binds
    abstract fun bindMenuOrderRepository(
        impl: MenuOrderRepositoryImpl
    ): MenuOrderRepository

    @Binds
    abstract fun bindOrderRepository(
        impl: OrderRepositoryImpl
    ): OrderRepository

    @Binds
    abstract fun bindOwnerRepository(
        impl: OwnerRepositoryImpl
    ): OwnerRepository

    @Binds
    abstract fun bindStoreRepository(
        impl: StoreRepositoryImpl
    ): StoreRepository
}
