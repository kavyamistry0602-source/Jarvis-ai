# JARVIS AI — converted from ULTRON

This project has been rebranded from ULTRON to JARVIS.

## Included
- JARVIS branding
- JARVIS personality configuration
- High-capability assistant persona configuration
- Feminine/cute/warm/lightly playful voice persona configuration
- Original ULTRON files preserved in `_original_ultron/`

## Important
The repository UI alone cannot create a truly powerful AI brain or a specific voice.
To make the assistant actually intelligent and speak, connect:
1. A capable AI model/API for reasoning.
2. Speech-to-text for the microphone.
3. Text-to-speech with a feminine voice.
4. Android permissions/APIs when packaging it as an app.

The `jarvis.config.json` is the central persona configuration to use when wiring the AI backend.

## Suggested architecture
Microphone -> Speech-to-Text -> AI model + jarvis.config.json -> Text-to-Speech -> JARVIS UI

## Android phase
After the web version works, package the interface as an Android app and add Android-specific voice/device integrations.
