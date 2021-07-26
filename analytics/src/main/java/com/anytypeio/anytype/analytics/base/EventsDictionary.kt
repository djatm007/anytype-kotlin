package com.anytypeio.anytype.analytics.base

object EventsDictionary {

    const val SCREEN_AUTH = "Auth Screen: Show"
    const val SCREEN_PROFILE = "Page: Profile"
    const val SCREEN_DASHBOARD = "Page: Dashboard"
    const val SCREEN_DOCUMENT = "Page: Document"
    const val SCREEN_KEYCHAIN = "Show Keychain Phrase Screen"
    const val SCREEN_NAVIGATION = "Show Navigation Screen"
    const val SCREEN_LINK_TO = "Show Link To Screen"
    const val SCREEN_MOVE_TO = "Show Move To Screen"
    const val SCREEN_SEARCH = "Show Search Screen"
    const val SCREEN_ARCHIVE = "Page: Archive"

    const val POPUP_ADD_BLOCK = "PopupNewBlockMenu"
    const val POPUP_STYLE = "PopupStyleMenu"
    const val POPUP_TURN_INTO = "PopupTurnIntoMenu"
    const val POPUP_ACTION_MENU = "PopupActionMenu"
    const val POPUP_MULTI_SELECT_MENU = "PopupMultiSelectMenu"
    const val POPUP_CHOOSE_EMOJI = "PopupChooseEmojiMenu"
    const val POPUP_DOCUMENT_MENU = "PopupDocumentMenu"
    const val POPUP_PROFILE_MENU = "PopupProfileMenu"
    const val POPUP_DOCUMENT_ICON_MENU = "PopupDocumentIconMenu"
    const val POPUP_PROFILE_ICON_MENU = "PopupProfileIconMenu"
    const val POPUP_MENTION_MENU = "PopupMentionMenu"
    const val POPUP_MARKUP_LINK = "PopupMarkupLinkMenu"
    const val POPUP_BOOKMARK = "PopupBookmarkMenu"
    const val POPUP_SLASH_MENU = "PopupSlashMenu"
    const val POPUP_SETTINGS = "PopupSettings"
    const val POPUP_OBJECT_TYPE_CHANGE = "PopupObjectTypeChange"
    const val POPUP_CHOOSE_COVER = "PopupChooseCover"
    const val POPUP_CHOOSE_LAYOUT = "PopupChooseLayout"

    const val BTN_PROFILE_WALLPAPER = "ButtonProfileWallpaper"
    const val BTN_PROFILE_PIN = "ButtonProfilePin"
    const val BTN_PROFILE_LOG_OUT = "ButtonProfileLogOut"
    const val BTN_PROFILE_BACK = "ButtonProfileBack"

    const val BTN_RANDOM_EMOJI = "ButtonRandomEmoji"
    const val BTN_REMOVE_EMOJI = "ButtonRemoveEmoji"
    const val BTN_UPLOAD_PHOTO = "ButtonUploadPhoto"

    const val BTN_MS_SELECT_ALL = "ButtonMSSelectAll"
    const val BTN_MS_UNSELECT_ALL = "ButtonMSUnselectAll"
    const val BTN_MS_DONE = "ButtonMSDone"
    const val BTN_MS_DELETE = "ButtonMSDelete"
    const val BTN_MS_TURN_INTO = "ButtonMSTurnInto"
    const val BTN_SCROLL_MOVE = "ButtonScrollMove"
    const val BTN_SCROLL_MOVE_CANCEL = "ButtonScrollMoveCancel"
    const val BTN_SCROLL_MOVE_MOVE = "ButtonScrollMoveMove"
    const val BTN_MS_COPY = "ButtonMSCopy"

    const val BTN_ENTER_MS = "ButtonMultiSelectMenu"
    const val BTN_ADD_BLOCK_MENU = "ButtonAddBlockMenu"
    const val BTN_HIDE_KEYBOARD = "ButtonHideKeyboard"
    const val BTN_STYLE_MENU = "ButtonStyleMenu"
    const val BTN_BLOCK_ACTIONS = "ButtonBlockActionsMenu"
    const val BTN_SLASH_MENU = "ButtonSlashMenu"

    const val PROP_STYLE = "style"
    const val PROP_TYPE = "objectType"
    const val PROP_LAYOUT = "layout"
    const val PROP_ACCOUNT_ID = "accountId"
    const val PROP_RELATION_FORMAT = "relationFormat"
    const val PROP_VIEWER_TYPE = "viewerType"

    const val WALLET_CREATE = "WalletCreate"
    const val WALLET_RECOVER = "WalletRecover"

    const val ACCOUNT_SELECT = "AccountSelect"
    const val ACCOUNT_CREATE = "AccountCreate"
    const val ACCOUNT_RECOVER = "AccountRecover"
    const val ACCOUNT_STOP = "AccountStop"

    const val PAGE_CREATE = "BlockCreatePage"
    const val OBJECT_CREATE = "BlockCreateObject"
    const val PAGE_MENTION_CREATE = "PageCreate"

    const val BLOCK_CREATE = "BlockCreate"
    const val BLOCK_REPLACE = "BlockReplace"
    const val BLOCK_DUPLICATE = "BlockListDuplicate"
    const val BLOCK_UNLINK = "BlockUnlink"
    const val BLOCK_SPLIT = "BlockSplit"
    const val BLOCK_MERGE = "BlockMerge"
    const val BLOCK_SET_TEXT_COLOR = "BlockSetTextColor"
    const val BLOCK_BACKGROUND_COLOR = "BlockListSetBackgroundColor"
    const val BLOCK_UPDATE_STYLE = "BlockSetTextStyle"
    const val BLOCK_UPDATE_CHECKBOX = "BlockSetTextChecked"
    const val BLOCK_UPDATE_TEXT = "BlockSetTextText"
    const val BLOCK_SET_ALIGN = "BlockListSetAlign"
    const val BLOCK_REDO = "BlockRedo"
    const val BLOCK_UNDO = "BlockUndo"
    const val BLOCK_UPDATE_TITLE = "BlockSetDetails"
    const val BLOCK_MOVE = "BlockListMove"
    const val BLOCK_TURN_INTO_DOCUMENT = "BlockListConvertChildrenToPages"
    const val BLOCK_DOWNLOAD_FILE = "DownloadFile"
    const val BLOCK_UPLOAD = "BlockUpload"
    const val BLOCK_SETUP_BOOKMARK = "BlockBookmarkFetch"
    const val BLOCK_PASTE = "BlockPaste"
    const val BLOCK_COPY = "BlockCopy"
    const val BLOCK_DIVIDER_UPDATE = "BlockListSetDivStyle"
    const val BLOCK_UPDATE_MARK = "BlockListSetTextMark"

    //region OBJECTS
    const val OBJECT_TYPE_CHANGE = "ObjectTypeChange"
    const val OBJECT_RELATION_CREATE = "ObjectRelationCreate"
    const val OBJECT_RELATION_ADD = "ObjectRelationAdd"
    //endregion

    //region SETS
    const val SCREEN_SET = "Set: Main"
    const val SETS_RELATION_CREATE = "SetRelationCreate"
    const val SETS_RELATION_ADD = "SetRelationAdd"
    //endregion

    //region SETS COMMANDS
    const val SETS_RECORD_CREATE = "SetRecordCreate"
    const val SETS_VIEWER_ACTIVE = "SetViewerActiveSet"
    const val SETS_VIEWER_RELATION_UPDATE = "SetViewerRelationUpdate"
    const val SETS_VIEWER_FILTER_UPDATE = "SetViewerFilterUpdate"
    const val SETS_VIEWER_SORT_UPDATE = "SetViewerSortUpdate"
    const val SETS_RELATION_TEXT_VALUE_UPDATE = "SetRelationTextValueUpdate"
    const val SETS_VIEWER_CREATE = "SetViewerCreate"
    //endregion

    //region DASHBOARD TABS
    const val TAB_FAVORITES = "FavoritesTabSelected"
    const val TAB_RECENT = "RecentTabSelected"
    const val TAB_INBOX = "InboxTabSelected"
    const val TAB_SETS = "SetsTabSelected"
    const val TAB_ARCHIVE = "ArchiveTabSelected"
    //endregion

    //region {OBJECT MENU BASE}
    const val BTN_OBJ_MENU_COVER = "ButtonCoverInObjectSettings"
    const val BTN_OBJ_MENU_ICON = "ButtonIconInObjectSettings"
    const val BTN_OBJ_MENU_RELATIONS = "ButtonRelationsInObjectSettings"
    const val BTN_OBJ_MENU_LAYOUT = "ButtonLayoutInObjectSettings"
    const val BTN_OBJ_MENU_ARCHIVE = "ButtonArchiveInObjectSettings"
    const val BTN_OBJ_MENU_RESTORE = "ButtonRestoreInObjectSettings"
    const val BTN_OBJ_MENU_FAVORITE = "ButtonFavoriteInObjectSettings"
    const val BTN_OBJ_MENU_UNFAVORITE = "ButtonUnfavoriteInObjectSettings"
    //endregion

    //region {SET MENU BASE}
    const val BTN_SET_MENU_COVER = "ButtonCoverInSetSettings"
    const val BTN_SET_MENU_ICON = "ButtonIconInSetSettings"
    const val BTN_SET_MENU_RELATIONS = "ButtonRelationsInSetSettings"
    const val BTN_SET_MENU_LAYOUT = "ButtonLayoutInSetSettings"
    const val BTN_SET_MENU_ARCHIVE = "ButtonArchiveInSetSettings"
    const val BTN_SET_MENU_RESTORE = "ButtonRestoreInSetSettings"
    const val BTN_SET_MENU_FAVORITE = "ButtonFavoriteInSetSettings"
    const val BTN_SET_MENU_UNFAVORITE = "ButtonUnfavoriteInSetSettings"
    //endregion

}