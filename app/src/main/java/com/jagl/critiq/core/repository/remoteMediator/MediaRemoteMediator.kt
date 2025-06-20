package com.jagl.critiq.core.repository.remoteMediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.RoomDatabase
import androidx.room.withTransaction
import com.jagl.critiq.core.local.entities.MediaEntity
import com.jagl.critiq.core.local.source.LocalMediaDataSource
import com.jagl.critiq.core.model.ApiResult
import com.jagl.critiq.core.remote.source.RemotePaginateMediaDataSource
import com.jagl.critiq.core.remote.utils.RequestData
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MediaRemoteMediator(
    private val database: RoomDatabase,
    private val remotePaginateMediaDataSource: RemotePaginateMediaDataSource,
    private val localMediaDataSource: LocalMediaDataSource,
    private val language: String? = null,
) : RemoteMediator<Int, MediaEntity>() {

    override suspend fun initialize(): InitializeAction {
        last = 1
        return super.initialize()
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MediaEntity>
    ): MediatorResult {
        val mediatorResult: MediatorResult = try {
            val page = when (loadType) {
                LoadType.REFRESH -> last
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    last++
                    last
                }
            }
            val result = remotePaginateMediaDataSource.getTrendings(page, language)
            if (result is ApiResult.Error) {
                val condition = result.message == RequestData.INVALID_PAGE_ERROR
                return if (condition) {
                    MediatorResult.Success(endOfPaginationReached = true)
                } else {
                    val throwable = Throwable(result.message)
                    throwable.printStackTrace()
                    MediatorResult.Error(throwable)
                }
            }

            result as ApiResult.Success

            val mediaList = result.data
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    localMediaDataSource.deleteAll()
                }
                localMediaDataSource.upsertAll(mediaList)
            }

            MediatorResult.Success(endOfPaginationReached = mediaList.isEmpty())
        } catch (e: IOException) {
            e.printStackTrace()
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            e.printStackTrace()
            MediatorResult.Error(e)
        }
        return mediatorResult
    }


    companion object {
        var last = 1
    }

}
