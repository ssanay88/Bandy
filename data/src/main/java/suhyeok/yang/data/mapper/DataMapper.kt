package suhyeok.yang.data.mapper

import com.yang.business.model.Band
import com.yang.business.model.ChatRoom
import com.yang.business.model.Comment
import com.yang.business.model.HomeTopBanner
import com.yang.business.model.Message
import com.yang.business.model.Posting
import com.yang.business.model.RecruitPosting
import com.yang.business.model.Region
import com.yang.business.model.User
import com.yang.business.model.UserSession
import suhyeok.yang.data.local.dto.UserSessionEntity
import suhyeok.yang.data.mapper.toBusinessCommentList
import suhyeok.yang.data.remote.dto.BandDTO
import suhyeok.yang.data.remote.dto.ChatRoomDTO
import suhyeok.yang.data.remote.dto.CommentDTO
import suhyeok.yang.data.remote.dto.HomeTopBannerDTO
import suhyeok.yang.data.remote.dto.MessageDTO
import suhyeok.yang.data.remote.dto.PostingDTO
import suhyeok.yang.data.remote.dto.RecruitPostingDTO
import suhyeok.yang.data.remote.dto.UserDTO
import suhyeok.yang.shared.common.util.DateTimeUtils
import suhyeok.yang.shared.common.util.toInstrument
import suhyeok.yang.shared.common.util.toSkillLevel
import suhyeok.yang.shared.common.util.toStr

/**
 * User Data Maaper
 */
fun UserDTO.toBusinessUser() = User(
    userId = this._userId,
    userName = this._userName,
    nickName = this._nickName,
    profileImageUrl = this._profileImageUrl,
    instrument = this._instrument,
    userDescription = this._userDescription,
    region = toRegionData(this._region),
    gender = this._gender,
    skillLevel = this._skillLevel,
    birthDate = DateTimeUtils.toLocalDate(this._birthDate),
    bandId = this._bandId,
    isLeader = this._isLeader,
    signupDate = DateTimeUtils.toLocalDate(this._signupDate),
    lastLoginDate = DateTimeUtils.toLocalDateTime(this._lastLoginDate)
)

fun User.toFirestoreUserDTO() = mapOf(
    "_userId" to this.userId,
    "_userName" to this.userName,
    "_nickName" to this.nickName,
    "_profileImageUrl" to this.profileImageUrl,
    "_instrument" to this.instrument,
    "_userDescription" to this.userDescription,
    "_region" to (this.region).toString(),
    "_gender" to this.gender,
    "_skillLevel" to this.skillLevel,
    "_birthDate" to DateTimeUtils.toIsoString(this.birthDate.atStartOfDay()),
    "_bandId" to this.bandId,
    "_isLeader" to this.isLeader,
    "_signupDate" to DateTimeUtils.toIsoString(this.signupDate.atStartOfDay()),
    "_lastLoginDate" to DateTimeUtils.toIsoString(this.lastLoginDate)
)

fun List<UserDTO>.toBusinessUserList() = this.map { it.toBusinessUser() }.toList()

fun List<User>.toFirestoreUserDTOList() = this.map { it.toFirestoreUserDTO() }.toList()

/**
 * Posting Data Mapper
 */
fun PostingDTO.toBusinessPosting() = Posting(
    postingId = this._postingId,
    title = this._title,
    content = this._content,
    postingType = this._postingType,
    postingAuthorInfo = this._postingAuthorInfo,
    viewCount = this._viewCount,
    commentCount = this._commentCount,
    createdAt = DateTimeUtils.toLocalDateTime(this._createdAt),
    updatedAt = DateTimeUtils.toLocalDateTime(this._updatedAt),
    comments = this._comments.toBusinessCommentList()
)

fun List<PostingDTO>.toBusinessPostingList() =
    this.map { it.toBusinessPosting() }.toList()

fun Posting.toFirestorePostingDTO() = mapOf(
    "_postingId" to this.postingId,
    "_title" to this.title,
    "_content" to this.content,
    "_postingType" to this.postingType,
    "_postingAuthorInfo" to this.postingAuthorInfo,
    "_viewCount" to this.viewCount,
    "_commentCount" to this.commentCount,
    "_createdAt" to DateTimeUtils.toIsoString(this.createdAt),
    "_updatedAt" to DateTimeUtils.toIsoString(this.updatedAt),
    "_comments" to this.comments.toFirestoreCommentDTO()
)

/**
 * Comment Data Mapper
 */
fun CommentDTO.toBusinessComment() = Comment(
    commentId = _commentId,
    postingId = _postingId,
    authorId = _authorId,
    content = _content,
    createdAt = DateTimeUtils.toLocalDateTime(_createdAt),
    updatedAt = DateTimeUtils.toLocalDateTime(_updatedAt)
)

fun List<CommentDTO>.toBusinessCommentList() =
    this.map { it.toBusinessComment() }.toList()

fun Comment.toFirestoreCommentDTO() = mapOf(
    "_commentId" to this.commentId,
    "_postingId" to this.postingId,
    "_authorId" to this.authorId,
    "_content" to this.content,
    "_createdAt" to DateTimeUtils.toIsoString(this.createdAt),
    "_updatedAt" to DateTimeUtils.toIsoString(this.updatedAt)
)

fun List<Comment>.toFirestoreCommentDTO() =
    this.map { it.toFirestoreCommentDTO() }.toList()


/**
 * Band Data Mapper
 */
fun BandDTO.toBusinessBand() = Band(
    bandId = this._bandId,
    bandName = this._bandName,
    bandProfileImageUrl = this._bandProfileImageUrl,
    coverImageUrl = this._coverImageUrl,
    bandDescription = this._bandDescription,
    members = (this._members).toBusinessUserList(),
    region = toRegionData(this._region),
    startDate = DateTimeUtils.toLocalDate(this._startDate),
    leaderId = this._leaderId,
    isRecruiting = this._isRecruiting,
    preferredGenres = this._preferredGenres,
    viewCount = this._viewCount,
    youtubeLink = this._youtubeLink,
    instagramLink = this._instagramLink,
    spotifyLink = this._spotifyLink
)

fun List<BandDTO>.toBusinessBandList() =
    this.map { it.toBusinessBand() }.toList()

fun Band.toFirestoreBandDTO() = mapOf(
    "_bandId" to this.bandId,
    "_bandName" to this.bandName,
    "_bandProfileImageUrl" to this.bandProfileImageUrl,
    "_coverImageUrl" to this.coverImageUrl,
    "_bandDescription" to this.bandDescription,
    "_members" to (this.members).toFirestoreUserDTOList(),
    "_region" to (this.region).toString(),
    "_startDate" to DateTimeUtils.toIsoString(this.startDate.atStartOfDay()),
    "_leaderId" to this.leaderId,
    "_isRecruiting" to this.isRecruiting,
    "_preferredGenres" to this.preferredGenres,
    "_viewCount" to this.viewCount,
    "_youtubeLink" to this.youtubeLink,
    "_instagramLink" to this.instagramLink,
    "_spotifyLink" to this.spotifyLink
)

/**
 * HomeTopBanner Data Mapper
 */
fun HomeTopBannerDTO.toBusinessHomeTopBanner() = HomeTopBanner(
    bannerImageUrl = this._bannerImageUrl,
    bannerTitle = this._bannerTitle,
    bannerDescription = this._bannerDescription,
    linkUrl = this._linkUrl
)

fun List<HomeTopBannerDTO>.toBusinessHomeTopBannerList() =
    this.map { it.toBusinessHomeTopBanner() }.toList()

/**
 * ChatRoom Data Mapper
 */
fun ChatRoomDTO.toBusinessChatRoom() = ChatRoom(
    chatRoomId = this._chatRoomId,
    chatRoomName = this._chatRoomName,
    roomImageUrl = this._roomImageUrl,
    participants = (this._participants).toBusinessUserList(),
    participantIds = this._participantIds,
    lastMessage = this._lastMessage,
    lastMessageTimestamp = DateTimeUtils.toLocalDateTime(this._lastMessageTimestamp),
    unreadMessageCount = this._unreadMessageCount,
    createdAt = DateTimeUtils.toLocalDateTime(this._createdAt),
    isGroupChat = this._isGroupChat,
    messages = this._messages.toBusinessMessageList()
)

fun List<ChatRoomDTO>.toBusinessChatRoomList() =
    this.map { it.toBusinessChatRoom() }.toList()

fun ChatRoom.toFirestoreChatRoomDTO() = mapOf(
    "_chatRoomId" to this.chatRoomId,
    "_chatRoomName" to this.chatRoomName,
    "_roomImageUrl" to this.roomImageUrl,
    "_participants" to (this.participants).toFirestoreUserDTOList(),
    "_participantIds" to this.participantIds,
    "_lastMessage" to this.lastMessage,
    "_lastMessageTimestamp" to if (this.lastMessageTimestamp != null) DateTimeUtils.toIsoString(
        this.lastMessageTimestamp!!
    ) else null,
    "_unreadMessageCount" to this.unreadMessageCount,
    "_createdAt" to DateTimeUtils.toIsoString(this.createdAt),
    "_isGroupChat" to this.isGroupChat,
    "_messages" to this.messages.toFirestoreMessageDTOList()
)

/**
 * Message Data Mapper
 */
fun MessageDTO.toBusinessMessage() = Message(
    messageId = this._messageId,
    senderId = this._senderId,
    chatRoomId = this._chatRoomId,
    content = this._content,
    timestamp = DateTimeUtils.toLocalDateTime(this._timestamp),
    unreadUserIds = this._unreadUserIds
)

fun List<MessageDTO>.toBusinessMessageList() =
    this.map { it.toBusinessMessage() }.toList()

fun Message.toFirestoreMessageDTO() = mapOf(
    "_messageId" to this.messageId,
    "_senderId" to this.senderId,
    "_chatRoomId" to this.chatRoomId,
    "_content" to this.content,
    "_timestamp" to DateTimeUtils.toIsoString(this.timestamp),
    "_unreadUserIds" to this.unreadUserIds
)

fun List<Message>.toFirestoreMessageDTOList() =
    this.map { it.toFirestoreMessageDTO() }.toList()

/**
 * RecruitPosting Data Mapper
 */
fun RecruitPostingDTO.toBusinessRecruitPosting() = RecruitPosting(
    recruitPostingId = this._recruitPostingId,
    title = this._title,
    content = this._content,
    postedBandId = this._postedBandId,
    postedBandName = this._postedBandName,
    postedBandImageUrl = this._postedBandImageUrl,
    authorId = this._authorId,
    targetAgeGroups = this._targetAgeGroups,
    targetGender = this._targetGender,
    targetRegion = toRegionData(this._targetRegion),
    targetSkillLevel = this._targetSkillLevel.toSkillLevel(),
    targetInstrument = this._targetInstrument,
    recruitingStatus = this._recruitingStatus,
    createdAt = DateTimeUtils.toLocalDateTime(this._createdAt),
    updatedAt = DateTimeUtils.toLocalDateTime(this._updatedAt),
    viewCount = this._viewCount,
    commentCount = this._commentCount
)

fun List<RecruitPostingDTO>.toBusinessRecruitPostingList() =
    this.map { it.toBusinessRecruitPosting() }.toList()

fun RecruitPosting.toFirestoreRecruitPostingDTO() = mapOf(
    "_recruitPostingId" to this.recruitPostingId,
    "_title" to this.title,
    "_content" to this.content,
    "_postedBandId" to this.postedBandId,
    "_postedBandName" to this.postedBandName,
    "_postedBandImageUrl" to this.postedBandImageUrl,
    "_authorId" to this.authorId,
    "_targetAgeGroups" to this.targetAgeGroups,
    "_targetGender" to this.targetGender,
    "_targetRegion" to this.targetRegion.toString(),
    "_targetSkillLevel" to this.targetSkillLevel.toStr(),
    "_targetInstrument" to this.targetInstrument,
    "_recruitingStatus" to this.recruitingStatus,
    "_createdAt" to DateTimeUtils.toIsoString(this.createdAt),
    "_updatedAt" to DateTimeUtils.toIsoString(this.updatedAt),
    "_viewCount" to this.viewCount,
    "_commentCount" to this.commentCount
)

fun toRegionData(regionString: String): Region {
    val (sido, sigungu) = regionString.split(" ")
    return Region(sido, sigungu)
}

/**
 * UserSession Data Mapper
 */
fun UserSessionEntity.toBusinessUserSession() = UserSession(
    isLogged = this._isLogged,
    userId = this._userId,
    userName = this._userName,
    userNickname = this._userNickname,
    userProfileImage = this._userProfileImage,
    userDescription = this._userDescription,
    userInstrument = this._userInstrument.toInstrument(),
    isLeader = this._isLeader,
    isBand = this._isBand,
    bandId = this._bandId
)

fun UserSession.toDataStoreUserSessionEntity() = UserSessionEntity(
    _isLogged = this.isLogged,
    _userId = this.userId,
    _userName = this.userName,
    _userNickname = this.userNickname,
    _userProfileImage = this.userProfileImage,
    _userDescription = this.userDescription,
    _userInstrument = this.userInstrument.toStr(),
    _isLeader = this.isLeader,
    _isBand = this.isBand,
    _bandId = this.bandId
)

fun User.toUserSession() = UserSession(
    isLogged = true,
    userId = this.userId,
    userName = this.userName,
    userNickname = this.nickName,
    userProfileImage = this.profileImageUrl,
    userDescription = this.userDescription,
    userInstrument = this.instrument,
    isLeader = this.isLeader,
    isBand = this.bandId.isNotEmpty(),
    bandId = this.bandId
)


