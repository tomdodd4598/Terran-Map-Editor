import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;

// 
// Decompiled by Procyon v0.5.36
// 

public class SaveMap
{
    public SaveMap(File file, final Connecter connect) {
        try {
            boolean write = true;
            if (!file.getName().endsWith(".twt")) {
                file = new File(String.valueOf(file.getPath()) + ".twt");
            }
            final File backup = file;
            if (file.exists()) {
                switch (connect.cp.confirmExtended(String.valueOf(file.getName()) + " " + connect.cp.txt(8) + "\n" + connect.cp.txt(9))) {
                    case 0: {
                        new File(String.valueOf(file.getParent()) + "\\Archive\\").mkdirs();
                        final File nFile = new File(String.valueOf(file.getParent()) + "\\Archive\\" + file.getName());
                        if (nFile.exists() && !connect.cp.confirm(String.valueOf(connect.cp.txt(46)) + " " + connect.cp.txt(8) + "\n" + connect.cp.txt(47))) {
                            break;
                        }
                        if (nFile.exists()) {
                            nFile.delete();
                        }
                        if (file.renameTo(nFile)) {
                            write = true;
                            break;
                        }
                        connect.cp.display(String.valueOf(connect.cp.txt(10)) + "\n" + connect.cp.txt(5) + " SM:F0:ko:ex", 2);
                        if (connect.cp.confirm(String.valueOf(connect.cp.txt(11)) + "\n" + connect.cp.txt(12))) {
                            write = true;
                            break;
                        }
                        break;
                    }
                    case 1: {
                        write = true;
                        break;
                    }
                    case 2: {
                        write = false;
                        break;
                    }
                }
            }
            else {
                write = true;
            }
            if (write) {
                String worldname = "";
                while ((worldname = connect.cp.askFor(connect.cp.txt(13))).equals("")) {
                    connect.cp.display(connect.cp.txt(14), 1);
                }
                final String worlddescription = connect.cp.askFor(connect.cp.txt(15));
                final String creator = connect.cp.askFor(connect.cp.txt(16));
                final PrintWriter p = new PrintWriter(new FileWriter(backup));
                final int players = connect.settings.getPlayerCount();
                final int ai = connect.settings.getAICount();
                final int count = connect.cp.countObjects();
                p.println("# " + creator + " " + new Date().toString());
                p.println(String.valueOf(this.convert(12 + players * 2)) + " WorldInfo");
                p.println("{");
                p.println("\tIsMultiplayerMap Bool True");
                p.println("\tMustAssembleFleet Bool True");
                p.println("\tWorld Description String 'IDGS_TPWORLDDESCRIPTION_MP_ARENA_SMALL'");
                p.println("\tWorldNameID String 'IDGS_TPWORLDNAMES_MP_ARENA_SMALL'");
                p.println("\tObject Count Int " + count);
                p.println("\tTeam List - Size Int 0");
                p.println("\tNumber of Players Int " + players);
                for (int i = 0; i < players; ++i) {
                    p.println("\tPlayerInfo - Player Name String 'New Player ( " + i + " )'");
                    p.println("\tPlayerInfo - TeamIndex Int -1");
                }
                p.println("\tIsCampaign Bool False");
                p.println("\tUse Custom World Name Bool True");
                p.println("\tCustom World Name String '" + worldname + "'");
                p.println("\tUse Custom World Description Bool True");
                p.println("\tCustom World Description String '" + worlddescription + "'");
                p.println("}");
                p.println("00000008 Game");
                p.println("{");
                p.println("\t00000002 Time");
                p.println("\t{");
                p.println("\t\tGame Tick Int 0");
                p.println("\t\tGame Time Double 0");
                p.println("\t}");
                p.println("\tFrame Int -1");
                p.println("\tPaused Bool False");
                p.println("\tActivePlayerIndex Int -1");
                p.println("}");
                p.println("00010000 World");
                p.println("{");
                p.println("\tWorldName String 'Zemyatin'");
                p.println("\tRandom Seed Int 0");
                int size = connect.map.getMapSize() / 2 + 500;
                p.println("\tWorld Size - Min Vector3( -" + size + ".000000, -" + size + ".000000, -500.000000 )");
                p.println("\tWorld Size - Max Vector3( " + size + ".000000, " + size + ".000000, 500.000000 )");
                p.println("\t# Player List");
                p.println("\tPlayerList Int " + (players + ai));
                for (int j = 0; j < players; ++j) {
                    p.println("\t00000032 Player");
                    p.println("\t{");
                    p.println("\t\tName String 'New Player ( " + j + " )'");
                    p.println("\t\tColor Colour( 0.000000, 0.502000, 0.502000, 1.000000 )");
                    p.println("\t\tIsPlayable Bool True");
                    p.println("\t\tIs Used In Game Bool False");
                    p.println("\t\tMultiplayer Name String ''");
                    p.println(connect.settings.getPlayer(j).getStartPoint());
                    p.println(connect.settings.getPlayer(j).getMatrix());
                    p.println("\t\tRace Int 4");
                    p.println("\t\tPoints Float 0.000000");
                    p.println("\t\tTeamIndex Int -1");
                    p.println("\t\tFormationType Int 2");
                    p.println("\t\t00000017 FleetAI");
                    p.println("\t\t{");
                    p.println("\t\t\t00000001 UPDATETIMER");
                    p.println("\t\t\t{");
                    p.println("\t\t\t\tStartTime Double 0");
                    p.println("\t\t\t}");
                    p.println("\t\t\t00000001 OFFSETTIMER");
                    p.println("\t\t\t{");
                    p.println("\t\t\t\tStartTime Double 0");
                    p.println("\t\t\t}");
                    p.println("\t\t\tOFFSETTIME Float 0.062500");
                    p.println("\t\t\tUPDATETIME Float 0.500000");
                    p.println("\t\t\t00000001 FORMATION");
                    p.println("\t\t\t{");
                    p.println("\t\t\t\tFORMATIONTYPE String 'None'");
                    p.println("\t\t\t}");
                    p.println("\t\t\tSHIPINFO - Size Int 0");
                    p.println("\t\t\tHOLDFIREACTIVE Bool False");
                    p.println("\t\t\tAITYPE String 'AIFLEET'");
                    p.println("\t\t}");
                    p.println("\t\tFlagIndex Int 0");
                    p.println("\t}");
                }
                for (int j = 0; j < ai; ++j) {
                    p.println("\t00000032 Player");
                    p.println("\t{");
                    p.println("\t\tName String 'New AI ( " + j + " )'");
                    p.println("\t\tColor Colour( 0.000000, 0.502000, 0.502000, 1.000000 )");
                    p.println("\t\tIsPlayable Bool False");
                    p.println("\t\tIs Used In Game Bool False");
                    p.println("\t\tMultiplayer Name String ''");
                    p.println("\t\tStartPoint Vector3( 0.000000, 0.000000, 0.000000 )");
                    p.println("\t\tStartPointForwardVector Vector3( 0.000000, 0.000000, 0.000000 )");
                    p.println("\t\tRace Int 4");
                    p.println("\t\tPoints Float 0.000000");
                    p.println("\t\tTeamIndex Int -1");
                    p.println("\t\tFormationType Int 2");
                    p.println("\t\t00000017 FleetAI");
                    p.println("\t\t{");
                    p.println("\t\t\t00000001 UPDATETIMER");
                    p.println("\t\t\t{");
                    p.println("\t\t\t\tStartTime Double 0");
                    p.println("\t\t\t}");
                    p.println("\t\t\t00000001 OFFSETTIMER");
                    p.println("\t\t\t{");
                    p.println("\t\t\t\tStartTime Double 0");
                    p.println("\t\t\t}");
                    p.println("\t\t\tOFFSETTIME Float 0.062500");
                    p.println("\t\t\tUPDATETIME Float 0.500000");
                    p.println("\t\t\t00000001 FORMATION");
                    p.println("\t\t\t{");
                    p.println("\t\t\t\tFORMATIONTYPE String 'None'");
                    p.println("\t\t\t}");
                    p.println("\t\t\tSHIPINFO - Size Int 0");
                    p.println("\t\t\tHOLDFIREACTIVE Bool False");
                    p.println("\t\t\tAITYPE String 'AIFLEET'");
                    p.println("\t\t}");
                    p.println("\t\tFlagIndex Int 0");
                    p.println("\t}");
                }
                p.println("\tNextID Int " + count);
                p.println("\t# World Object List");
                p.println("\tWorldObjects Int " + count);
                for (int j = 0; j < count; ++j) {
                    final GObject go = connect.cp.getGO(j);
                    p.println("\tID Int " + (j + 1));
                    p.println("\tType String '" + go.getID() + "'");
                    p.println("\t00000014 State");
                    p.println("\t{");
                    p.println("\t\tHasState Bool False");
                    p.println(go.getVector());
                    p.println(go.getMatrix());
                    p.println("\t\tPlayerIndex Int -1");
                    p.println("\t\t# AIEntity");
                    p.println("\t\tType String ''");
                    p.println("\t\t# RenderEntity");
                    p.println("\t\tType String ''");
                    p.println("\t\t# PhysicsEntity");
                    p.println("\t\tType String ''");
                    p.println("\t\t# CollisionEntity");
                    p.println("\t\tType String ''");
                    p.println("\t\t# CustomInfoEntity");
                    p.println("\t\tType String ''");
                    p.println("\t}");
                    go.setIndex(j + 1);
                }
                p.println("\t00000900 GameSpecific");
                p.println("\t{");
                p.println("\t\tWorld Description Sting ID String 'IDGS_TPWORLDDESCRIPTION_MP_ARENA_SMALL'");
                p.println("\t\tWorld Name Sting ID String 'IDGS_TPWORLDNAMES_MP_ARENA_SMALL'");
                p.println("\t\t00000001 Effect Event Keeper");
                p.println("\t\t{");
                p.println("\t\t\tNumEffectEventInfoChunks Int 0");
                p.println("\t\t}");
                p.println("\t\tSkybox mesh name String 'skybox_08_Layer1'");
                p.println("\t\tAmbient Light Colour( 0.090196, 0.090196, 0.090196, 1.000000 )");
                p.println("\t\tVector for roof light orientation Vector3( -0.593311, 0.549935, -0.587838 )");
                p.println("\t\tHemispherical floor light color Colour( 0.250980, 0.509804, 0.180392, 1.000000 )");
                p.println("\t\tHemispherical roof light color Colour( 0.992157, 0.996078, 0.909804, 1.000000 )");
                p.println("\t\tWorld Initialized State Bool False ");
                p.println("\t\tWorld Buffer Size Float 500.000000");
                p.println("\t\tWaypoint Path Info Vector - Size Int 0");
                p.println("\t\tWorld Polygons Vectors - Size Int 0");
                p.println("\t\tWorld Point Sets Vector - Size Int 0");
                p.println("\t\tFlag List - Size Int 0");
                p.println("\t\tTimer List - Size Int 0");
                p.println("\t\tSpeech Event List - Size Int 0");
                int c = 0;
                for (int k = 0; k < players; ++k) {
                    for (int l = 0; l < ai; ++l) {
                        if (connect.diplo.getRelat(k + 1, l + 1)) {
                            ++c;
                        }
                    }
                }
                for (int column = players + 1; column <= ai + players; ++column) {
                    for (int row = 1; row <= ai; ++row) {
                        if (column - players > row && connect.diplo.getRelat(column, row)) {
                            ++c;
                        }
                    }
                }
                p.println("\t\tPlayerAllianceInfoVector - Size Int " + c);
                for (int k = 0; k < players; ++k) {
                    for (int l = 0; l < ai; ++l) {
                        if (connect.diplo.getRelat(k + 1, l + 1)) {
                            p.println("\t\t00000002 PlayerAllianceInfoVector - Element");
                            p.println("\t\t{");
                            p.println("\t\t\tPlayer0 Int " + (k + 1));
                            p.println("\t\t\tPlayer1 Int " + (l + players + 1));
                            p.println("\t\t}");
                        }
                    }
                }
                for (int column = players + 1; column <= ai + players; ++column) {
                    for (int row = 1; row <= ai; ++row) {
                        if (column - players > row && connect.diplo.getRelat(column, row)) {
                            p.println("\t\t00000002 PlayerAllianceInfoVector - Element");
                            p.println("\t\t{");
                            p.println("\t\t\tPlayer0 Int " + column);
                            p.println("\t\t\tPlayer1 Int " + (row + players));
                            p.println("\t\t}");
                        }
                    }
                }
                p.println("\t\tTeam List - Size Int 0");
                p.println("\t\tWinning Team Int -1");
                p.println("\t\tNum Groups Int " + (players + ai));
                for (int k = 0; k < players + ai; ++k) {
                    size = connect.settings.getPlayer(k).getSize();
                    p.println("\t\t" + this.convert(size + 2) + " Group");
                    p.println("\t\t{");
                    p.println("\t\t\tName String 'Group" + k + "'");
                    p.println("\t\t\tWorld Object IDs - Size Int " + size);
                    for (c = 0; c < size; ++c) {
                        p.println("\t\t\tWorld Object IDs - Element Int " + connect.settings.getPlayer(k).getGO(c).getIndex());
                    }
                    p.println("\t\t}");
                }
                p.println("\t\t00000500 World Rules");
                p.println("\t\t{");
                p.println("\t\t\tRule List Int 3");
                p.println("\t\t\tRule Name String 'Init World ( Hidden From Designers )'");
                p.println("\t\t\tRun Once Bool True");
                p.println("\t\t\tIs Active Bool True");
                p.println("\t\t\tNumConditions Int 1");
                p.println("\t\t\t00000001 Condition List");
                p.println("\t\t\t{");
                p.println("\t\t\t\tType String 'World Initialize'");
                p.println("\t\t\t}");
                p.println("\t\t\tNumActions Int " + (players + ai));
                for (int k = 0; k < players; ++k) {
                    p.println("\t\t\t00000003 Action List");
                    p.println("\t\t\t{");
                    p.println("\t\t\t\tType String 'Set Group/Unit Owner'");
                    p.println("\t\t\t\tGroup/Unit Name String 'Group" + k + "'");
                    p.println("\t\t\t\tNew Owner String 'New Player ( " + k + " )'");
                    p.println("\t\t\t}");
                }
                for (int k = 0; k < ai; ++k) {
                    p.println("\t\t\t00000003 Action List");
                    p.println("\t\t\t{");
                    p.println("\t\t\t\tType String 'Set Group/Unit Owner'");
                    p.println("\t\t\t\tGroup/Unit Name String 'Group" + (players + k) + "'");
                    p.println("\t\t\t\tNew Owner String 'New AI ( " + k + " )'");
                    p.println("\t\t\t}");
                }
                p.println("\t\t\tRule Name String 'END'");
                p.println("\t\t\tRun Once Bool True");
                p.println("\t\t\tIs Active Bool True");
                p.println("\t\t\tNumConditions Int 1");
                p.println("\t\t\t00000001 Condition List");
                p.println("\t\t\t{");
                p.println("\t\t\t\tType String 'Skirmish Game Complete'");
                p.println("\t\t\t}");
                p.println("\t\t\tNumActions Int 1");
                p.println("\t\t\t00000005 Action List");
                p.println("\t\t\t{");
                p.println("\t\t\t\tType String 'End Game'");
                p.println("\t\t\t\tUse Custom Message String 'FALSE'");
                p.println("\t\t\t\tWinner - Custom Message String ID String 'GAME STRING'");
                p.println("\t\t\t\tLoser - Custom Message String ID String 'GAME STRING'");
                p.println("\t\t\t\tShow Stats Screen String 'TRUE'");
                p.println("\t\t\t}");
                p.println("\t\t\tRule Name String 'setob'");
                p.println("\t\t\tRun Once Bool True");
                p.println("\t\t\tIs Active Bool True");
                p.println("\t\t\tNumConditions Int 1");
                p.println("\t\t\t00000001 Condition List");
                p.println("\t\t\t{");
                p.println("\t\t\t\tType String 'World Initialize'");
                p.println("\t\t\t}");
                p.println("\t\t\tNumActions Int 2");
                p.println("\t\t\t00000003 Action List");
                p.println("\t\t\t{");
                p.println("\t\t\t\tType String 'Set Objective Task Active State'");
                p.println("\t\t\t\tObjective Task String 'killem'");
                p.println("\t\t\t\tActive State String 'TRUE'");
                p.println("\t\t\t}");
                p.println("\t\t\t00000006 Action List");
                p.println("\t\t\t{");
                p.println("\t\t\t\tType String 'Play Music Track'");
                p.println("\t\t\t\tFile Name String 'BTL_NavyBig_BassnDrums'");
                p.println("\t\t\t\tCrossfade transition String 'TRUE'");
                p.println("\t\t\t\tFade Out Time ( secs ) Float 2.000000");
                p.println("\t\t\t\tFade In Time ( secs ) Float 2.000000");
                p.println("\t\t\t\tNew Volume ( 0 to 1 ) Float 0.700000");
                p.println("\t\t\t}");
                p.println("\t\t}");
                p.println("\t\t00000012 Objective System");
                p.println("\t\t{");
                p.println("\t\t\tCurrent Objective Point Int -1");
                p.println("\t\t\tCurrent Point Visible On StarMap Bool True");
                p.println("\t\t\tObjective Point Info - Size Int 0");
                p.println("\t\t\tObjective Task Array - Size Int 1");
                p.println("\t\t\t00000005 Objective Task Array - Element");
                p.println("\t\t\t{");
                p.println("\t\t\t\tName String 'killem'");
                p.println("\t\t\t\tTextStringID String 'IDGS_TPOBJECTIVES2_MP_DESTROYENEMYSHIPS'");
                p.println("\t\t\t\tActive Bool False");
                p.println("\t\t\t\tCompleted Bool False");
                p.println("\t\t\t\tFailed Bool False");
                p.println("\t\t\t}");
                p.println("\t\t}");
                p.println("\t\t00000001 Rope");
                p.println("\t\t{");
                p.println("\t\t\tRopeInfo - Size Int 0");
                p.println("\t\t}");
                p.println("\t\t00000001 Grappled Objects");
                p.println("\t\t{");
                p.println("\t\t\tGrappled Objects Info - Size Int 0");
                p.println("\t\t}");
                p.println("\t\t00000001 Boarding Actions");
                p.println("\t\t{");
                p.println("\t\t\tBoarding Actions Info - Size Int 0");
                p.println("\t\t}");
                p.println("\t\t00000008 Journal Entry");
                p.println("\t\t{");
                p.println("\t\t\tPage Info - Size Int 1");
                p.println("\t\t\t00000003 Page Info - Element");
                p.println("\t\t\t{");
                p.println("\t\t\t\tTextStringID String 'IDGS_TPSPEECHEVENTSJOURNALS_J01JIM01'");
                p.println("\t\t\t\tSpeechEventFileName String 'J01JIM01'");
                p.println("\t\t\t\tPictureTexture String 'Journal_Mission1_Shot1'");
                p.println("\t\t\t}");
                p.println("\t\t\tTitle StringID String 'IDGS_TPJOURNALSCREEN_DUMMY_TITLE'");
                p.println("\t\t}");
                p.println("\t\t00000001 World Map");
                p.println("\t\t{");
                p.println("\t\t\tBackdrop Texture Name String 'Map_Zemyatin'");
                p.println("\t\t}");
                p.println("\t\tCan Assemble Fleets Bool True");
                p.println("\t\tWorld Crew List - Size Int 0");
                p.println("\t\tWorld Arms List - Size Int 0");
                p.println("\t\t00000001 MapText System");
                p.println("\t\t{");
                p.println("\t\t\tMapText Point Info - Size Int 0");
                p.println("\t\t}");
                p.println("\t\tREADAIENTITYCOUNTS Bool False");
                p.println("\t\tJournal Music Name String 'GameMusicTemp'");
                p.println("\t\tPlayEndMovie Bool False");
                p.println("\t\tIsCampaign Bool False");
                p.println("\t\tIs Alliance Change Allowed Bool True");
                p.println("\t\tUse Custom World Name Bool True");
                p.println("\t\tCustom World Name String '" + worldname + "'");
                p.println("\t\tUse Custom World Description Bool True");
                p.println("\t\tCustom World Description String '" + worlddescription + "'");
                p.println("\t\tIslands Make Sounds Bool True");
                p.println("\t\tDATA_NEBULA_CAMERA_EFFECT Int 0");
                p.println("\t\tDATA_NEXT_NEBULA_CAMERA_EFFECT Int 0");
                p.println("\t\t00000001 DATA_NEBULA_CAMERA_EFFECT_FADE_IN_TIMER");
                p.println("\t\t{");
                p.println("\t\t\tStartTime Double 0");
                p.println("\t\t}");
                p.println("\t\t00000001 DATA_NEBULA_CAMERA_EFFECT_SPIN_TIMER");
                p.println("\t\t{");
                p.println("\t\t\tStartTime Double 0");
                p.println("\t\t}");
                p.println("\t}");
                p.println("}");
                final BufferedReader br = new BufferedReader(new FileReader(new File("Data/Map.add")));
                String input;
                while ((input = br.readLine()) != null) {
                    p.println(input);
                }
                p.flush();
                p.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            connect.cp.display(String.valueOf(connect.cp.txt(5)) + " SM:00:EN:DE\n" + connect.cp.txt(17) + "\n" + connect.cp.txt(18), 0);
        }
    }
    
    private String convert(final int c) {
        String number = Integer.toString(c);
        for (int i = number.length(); i < 8; ++i) {
            number = "0" + number;
        }
        return number;
    }
}
