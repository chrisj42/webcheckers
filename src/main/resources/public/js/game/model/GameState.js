/**
 * This module exports the GameState class constructor.
 * 
 * This Value Object holds a snapshot of the state of the checkers game.
 * Information about who the players are, the current player and whose
 * turn is it.
 */
define(function(require){
  'use strict';

  // imports
  const BrowserUtils = require('../util/BrowserUtils');

  /**
   * Constructor function.
   */
  function GameState(gameData) {

    // public (internal) methods

    this.getGameID = function getGameID() {
      return BrowserUtils.getParameterByName('gameID') || gameData.gameID;
    };

    this.getViewMode = function getViewMode() {
      return gameData.viewMode;
    };

    this.getModeOptions = function getModeOptions() {
      return gameData.modeOptions;
    };

    this.getModeOption = function getModeOption(optionName) {
      return gameData.modeOptions[optionName];
    };

    this.getCurrentUser = function getCurrentUser() {
      return gameData.currentUser;
    };

    this.getRedPlayer = function getRedPlayer() {
      return gameData.redPlayer;
    };

    this.getWhitePlayer = function getWhitePlayer() {
      return gameData.whitePlayer;
    };

    this.getActivePlayer = function getActivePlayer() {
      return this.isRedsTurn() ? gameData.redPlayer : gameData.whitePlayer;
    };

    this.getOpponentPlayer = function getOpponentPlayer() {
      return this.isPlayerRed() ? gameData.whitePlayer : gameData.redPlayer;
    };

    this.isRedsTurn = function isRedsTurn() {
      return gameData.activeColor === 'RED';
    };
    
    this.isPlayerRed = function isPlayerRed() {
      return gameData.redPlayer === gameData.currentUser;
    };

    this.isPlayerWhite = function isPlayerWhite() {
      return gameData.whitePlayer === gameData.currentUser;
    };

  }

  //
  // Public (external) methods
  //

  /**
   * The name of the modeOption that states when the game is over.
   */
  GameState.IS_END_OPTION = "isGameOver";

  /**
   * The name of the modeOption that states when the game is over.
   */
  GameState.END_MESSAGE_OPTION = "gameOverMessage";

  /**
   * Queries whether this state is valid for the Play mode.
   */
  GameState.prototype.isValidInPlayMode = function isValidInPlayMode() {
      return (this.getCurrentUser() === this.getRedPlayer()
          || this.getCurrentUser() === this.getWhitePlayer());
  };

  /**
   * Queries whether the game is over; IOWs someone won or resgined.
   */
  GameState.prototype.isGameOver = function isGameOver() {
      return this.getModeOption(GameState.IS_END_OPTION) || false;
  };

  /**
   * Supplies the 'end of game' message.
   */
  GameState.prototype.getGameOverMessage = function getGameOverMessage() {
    if (!this.isGameOver()) {
      throw new Error("Game isn't over yet.")
    }
    return this.getModeOption(GameState.END_MESSAGE_OPTION) || "Game over, man!";
  };

    /**
   * Queries whether it's the current player's turn.
   */
  GameState.prototype.isMyTurn = function isMyTurn() {
    return (this.isPlayerRed() && this.isRedsTurn())
    || (this.isPlayerWhite() && !this.isRedsTurn());
  };
  
  // export class constructor
  return GameState;
  
});
