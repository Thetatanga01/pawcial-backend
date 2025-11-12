-- ============================================================
-- Flyway Migration: V2__PAWCIAL_DICTIONARIES.sql
-- Description: PAWCIAL Dictionary Data - Lookup Tables
-- Author: System
-- Date: 2025-01-19
-- ============================================================

-- ============================================================
-- DICT_SEX
-- ============================================================
INSERT INTO PAWCIAL.DICT_SEX (CODE, LABEL) VALUES
                                               ('MALE', 'Erkek'),
                                               ('FEMALE', 'Dişi'),
                                               ('UNKNOWN', 'Bilinmiyor')
ON CONFLICT (CODE) DO NOTHING;

-- ============================================================
-- DICT_DOMESTIC_STATUS
-- ============================================================
INSERT INTO PAWCIAL.DICT_DOMESTIC_STATUS (CODE, LABEL) VALUES
                                                           ('DOMESTICATED', 'Evcil'),
                                                           ('WILD', 'Vahşi'),
                                                           ('ANCESTOR', 'Atalar')
ON CONFLICT (CODE) DO NOTHING;

-- ============================================================
-- DICT_FACILITY_TYPE
-- ============================================================
INSERT INTO PAWCIAL.DICT_FACILITY_TYPE (CODE, LABEL) VALUES
                                                         ('SHELTER', 'Barınak'),
                                                         ('CLINIC', 'Veteriner Kliniği'),
                                                         ('DEPOT', 'Depo/Merkez')
ON CONFLICT (CODE) DO NOTHING;

-- ============================================================
-- DICT_ZONE_PURPOSE
-- ============================================================
INSERT INTO PAWCIAL.DICT_ZONE_PURPOSE (CODE, LABEL) VALUES
                                                        ('GENERAL', 'Genel'),
                                                        ('QUARANTINE', 'Karantina'),
                                                        ('MEDICAL', 'Medikal'),
                                                        ('NURSERY', 'Yavru Bakım')
ON CONFLICT (CODE) DO NOTHING;

-- ============================================================
-- DICT_UNIT_TYPE
-- ============================================================
INSERT INTO PAWCIAL.DICT_UNIT_TYPE (CODE, LABEL) VALUES
                                                     ('KENNEL', 'Kafes'),
                                                     ('PADDOCK', 'Açık Alan'),
                                                     ('ROOM', 'Oda'),
                                                     ('MEDICAL', 'Medikal Ünitesi'),
                                                     ('ISOLATION', 'İzolasyon')
ON CONFLICT (CODE) DO NOTHING;

-- ============================================================
-- DICT_ASSET_TYPE
-- ============================================================
INSERT INTO PAWCIAL.DICT_ASSET_TYPE (CODE, LABEL) VALUES
                                                      ('ELECTRONICS', 'Elektronik'),
                                                      ('FIXTURE', 'Demirbaş'),
                                                      ('TOOL', 'Araç/Gereç'),
                                                      ('VEHICLE', 'Araç')
ON CONFLICT (CODE) DO NOTHING;

-- ============================================================
-- DICT_ASSET_STATUS
-- ============================================================
INSERT INTO PAWCIAL.DICT_ASSET_STATUS (CODE, LABEL) VALUES
                                                        ('ACTIVE', 'Aktif'),
                                                        ('IN_REPAIR', 'Tamirde'),
                                                        ('RETIRED', 'Hurdaya Çıkmış')
ON CONFLICT (CODE) DO NOTHING;

-- ============================================================
-- DICT_SERVICE_TYPE
-- ============================================================
INSERT INTO PAWCIAL.DICT_SERVICE_TYPE (CODE, LABEL) VALUES
                                                        ('MAINTENANCE', 'Bakım'),
                                                        ('REPAIR', 'Tamir'),
                                                        ('CALIBRATION', 'Kalibrasyon')
ON CONFLICT (CODE) DO NOTHING;

-- ============================================================
-- DICT_EVENT_TYPE
-- ============================================================
INSERT INTO PAWCIAL.DICT_EVENT_TYPE (CODE, LABEL) VALUES
                                                      ('INTAKE', 'Giriş'),
                                                      ('OUTCOME', 'Çıkış'),
                                                      ('TRANSFER', 'Transfer'),
                                                      ('HOLD_START', 'Bekleme Başlangıcı'),
                                                      ('HOLD_END', 'Bekleme Bitişi'),
                                                      ('NOTE', 'Not'),
                                                      ('RESP_ASSIGN', 'Sorumluluk Atama'),
                                                      ('RESP_RELEASE', 'Sorumluluk Bırakma')
ON CONFLICT (CODE) DO NOTHING;

-- ============================================================
-- DICT_SOURCE_TYPE
-- ============================================================
INSERT INTO PAWCIAL.DICT_SOURCE_TYPE (CODE, LABEL) VALUES
                                                       ('STRAY', 'Sokaktan'),
                                                       ('SURRENDER', 'Teslim Edildi'),
                                                       ('TRANSFER_IN', 'Transfer Geldi'),
                                                       ('SEIZURE', 'El Konuldu'),
                                                       ('BORN', 'Doğdu')
ON CONFLICT (CODE) DO NOTHING;

-- ============================================================
-- DICT_OUTCOME_TYPE
-- ============================================================
INSERT INTO PAWCIAL.DICT_OUTCOME_TYPE (CODE, LABEL) VALUES
                                                        ('ADOPTION', 'Sahiplenme'),
                                                        ('RETURN_TO_OWNER', 'Sahibine İade'),
                                                        ('TRANSFER_OUT', 'Transfer Gitti'),
                                                        ('EUTHANASIA', 'Ötenazi'),
                                                        ('DIED', 'Öldü'),
                                                        ('ESCAPE', 'Kaçtı'),
                                                        ('FOSTER', 'Geçici Yuva')
ON CONFLICT (CODE) DO NOTHING;

-- ============================================================
-- DICT_HOLD_TYPE
-- ============================================================
INSERT INTO PAWCIAL.DICT_HOLD_TYPE (CODE, LABEL) VALUES
                                                     ('QUARANTINE', 'Karantina'),
                                                     ('MEDICAL', 'Medikal'),
                                                     ('BEHAVIOR', 'Davranış'),
                                                     ('COURT_HOLD', 'Mahkeme Kararı'),
                                                     ('BITE_CASE', 'Isırma Vakası')
ON CONFLICT (CODE) DO NOTHING;

-- ============================================================
-- DICT_SIZE
-- ============================================================
INSERT INTO PAWCIAL.DICT_SIZE (CODE, LABEL) VALUES
                                                ('SMALL', 'Küçük'),
                                                ('MEDIUM', 'Orta'),
                                                ('LARGE', 'Büyük'),
                                                ('GIANT', 'Dev')
ON CONFLICT (CODE) DO NOTHING;

-- ============================================================
-- DICT_TRAINING_LEVEL
-- ============================================================
INSERT INTO PAWCIAL.DICT_TRAINING_LEVEL (CODE, LABEL) VALUES
                                                          ('NONE', 'Yok'),
                                                          ('BASIC', 'Temel'),
                                                          ('INTERMEDIATE', 'Orta'),
                                                          ('ADVANCED', 'İleri'),
                                                          ('WORKING', 'Çalışan Köpek')
ON CONFLICT (CODE) DO NOTHING;

-- ============================================================
-- DICT_TEMPERAMENT
-- ============================================================
INSERT INTO PAWCIAL.DICT_TEMPERAMENT (CODE, LABEL) VALUES
                                                       ('FRIENDLY', 'Arkadaş Canlısı'),
                                                       ('ENERGETIC', 'Enerjik'),
                                                       ('LAP_LOVER', 'Kucak Sever'),
                                                       ('CALM', 'Sakin'),
                                                       ('PLAYFUL', 'Oyuncu'),
                                                       ('SHY', 'Utangaç'),
                                                       ('PROTECTIVE', 'Koruyucu'),
                                                       ('INDEPENDENT', 'Bağımsız')
ON CONFLICT (CODE) DO NOTHING;

-- ============================================================
-- DICT_HEALTH_FLAG
-- ============================================================
INSERT INTO PAWCIAL.DICT_HEALTH_FLAG (CODE, LABEL) VALUES
                                                       ('VACCINATED', 'Aşılı'),
                                                       ('NEUTERED', 'Kısırlaştırılmış'),
                                                       ('SPECIAL_DIET', 'Özel Diyet'),
                                                       ('CHRONIC_COND', 'Kronik Hastalık'),
                                                       ('MEDICATIONS', 'İlaç Kullanımı'),
                                                       ('ALLERGIES', 'Alerjiler')
ON CONFLICT (CODE) DO NOTHING;

-- ============================================================
-- DICT_COLOR
-- ============================================================
INSERT INTO PAWCIAL.DICT_COLOR (CODE, LABEL) VALUES
                                                 ('GOLDEN', 'Altın Sarısı'),
                                                 ('BLACK', 'Siyah'),
                                                 ('WHITE', 'Beyaz'),
                                                 ('BROWN', 'Kahverengi'),
                                                 ('BRINDLE', 'Kaplan Desenli'),
                                                 ('GRAY', 'Gri'),
                                                 ('TAN', 'Ten Rengi'),
                                                 ('CREAM', 'Krem'),
                                                 ('TRICOLOR', 'Üç Renkli'),
                                                 ('BICOLOR', 'İki Renkli')
ON CONFLICT (CODE) DO NOTHING;

-- ============================================================
-- DICT_MED_EVENT_TYPE
-- ============================================================
INSERT INTO PAWCIAL.DICT_MED_EVENT_TYPE (CODE, LABEL) VALUES
                                                          ('VACCINE', 'Aşı'),
                                                          ('EXAM', 'Muayene'),
                                                          ('TREATMENT', 'Tedavi'),
                                                          ('SURGERY', 'Ameliyat'),
                                                          ('LAB', 'Laboratuvar')
ON CONFLICT (CODE) DO NOTHING;

-- ============================================================
-- DICT_VACCINE
-- ============================================================
INSERT INTO PAWCIAL.DICT_VACCINE (CODE, LABEL) VALUES
                                                   ('RABIES', 'Kuduz'),
                                                   ('DA2PP', 'Köpek 5li'),
                                                   ('FVRCP', 'Kedi 3lü'),
                                                   ('LEPTOSPIROSIS', 'Leptospiroz'),
                                                   ('FeLV', 'Kedi Lösemi'),
                                                   ('BORDETELLA', 'Köpek Öksürüğü'),
                                                   ('FELINE_LEUKEMIA', 'Kedi Lösemisi'),
                                                   ('CANINE_INFLUENZA', 'Köpek Gribi')
ON CONFLICT (CODE) DO NOTHING;

-- ============================================================
-- DICT_DOSE_ROUTE
-- ============================================================
INSERT INTO PAWCIAL.DICT_DOSE_ROUTE (CODE, LABEL) VALUES
                                                      ('IM', 'Kas İçi'),
                                                      ('SC', 'Deri Altı'),
                                                      ('PO', 'Ağızdan'),
                                                      ('TOPICAL', 'Deri Üzerine'),
                                                      ('IV', 'Damar İçi'),
                                                      ('ID', 'Deri İçi')
ON CONFLICT (CODE) DO NOTHING;

-- ============================================================
-- DICT_PLACEMENT_TYPE
-- ============================================================
INSERT INTO PAWCIAL.DICT_PLACEMENT_TYPE (CODE, LABEL) VALUES
                                                          ('FOSTER', 'Geçici Yuva'),
                                                          ('ADOPTION', 'Kalıcı Sahiplenme'),
                                                          ('TRIAL_ADOPTION', 'Deneme Sahiplenme')
ON CONFLICT (CODE) DO NOTHING;

-- ============================================================
-- DICT_PLACEMENT_STATUS
-- ============================================================
INSERT INTO PAWCIAL.DICT_PLACEMENT_STATUS (CODE, LABEL) VALUES
                                                            ('ACTIVE', 'Aktif'),
                                                            ('COMPLETED', 'Tamamlandı'),
                                                            ('RETURNED', 'İade Edildi'),
                                                            ('CANCELLED', 'İptal Edildi')
ON CONFLICT (CODE) DO NOTHING;

-- ============================================================
-- DICT_OBSERVATION_CATEGORY
-- ============================================================
INSERT INTO PAWCIAL.DICT_OBSERVATION_CATEGORY (CODE, LABEL) VALUES
                                                                ('TEMPERAMENT', 'Mizaç'),
                                                                ('BEHAVIOR', 'Davranış'),
                                                                ('HEALTH', 'Sağlık'),
                                                                ('DIET', 'Beslenme'),
                                                                ('SKIN', 'Cilt'),
                                                                ('EYES', 'Gözler'),
                                                                ('EARS', 'Kulaklar'),
                                                                ('LEGS', 'Bacaklar'),
                                                                ('MOBILITY', 'Hareket'),
                                                                ('DENTAL', 'Diş'),
                                                                ('GENERAL', 'Genel')
ON CONFLICT (CODE) DO NOTHING;

-- ============================================================
-- DICT_VOLUNTEER_AREA
-- ============================================================
INSERT INTO PAWCIAL.DICT_VOLUNTEER_AREA (CODE, LABEL, DESCRIPTION) VALUES
                                                                       ('RESCUE', 'Kurtarma', 'Sokak hayvanlarını kurtarma ve barınağa getirme'),
                                                                       ('FOSTER', 'Geçici Yuva', 'Evinde geçici yuva sağlama'),
                                                                       ('ADOPTION_COORD', 'Sahiplendirme Koordinasyonu', 'Sahiplendirme görüşmeleri ve takibi'),
                                                                       ('FEEDING', 'Besleme', 'Sokak hayvanlarını besleme, mama dağıtımı'),
                                                                       ('MEDICAL_ASSIST', 'Tıbbi Yardım', 'Veteriner kliniğine götürme, tedavi takibi'),
                                                                       ('TRANSPORT', 'Ulaşım', 'Hayvan taşıma, veteriner transferleri'),
                                                                       ('GROOMING', 'Bakım', 'Tüy kesimi, banyo, tırnak bakımı'),
                                                                       ('TRAINING', 'Eğitim', 'Temel itaat eğitimi'),
                                                                       ('SOCIALIZATION', 'Sosyalizasyon', 'Hayvanları insanlara ve diğer hayvanlara alıştırma'),
                                                                       ('PHOTOGRAPHY', 'Fotoğrafçılık', 'Sahiplendirme için fotoğraf çekimi'),
                                                                       ('ADMIN', 'İdari İşler', 'Evrak işleri, kayıt tutma'),
                                                                       ('FUNDRAISING', 'Bağış Toplama', 'Etkinlik organizasyonu, bağış kampanyaları'),
                                                                       ('FACILITY_MAINT', 'Tesis Bakımı', 'Barınak temizliği ve bakımı'),
                                                                       ('HOME_VISIT', 'Ev Ziyareti', 'Sahiplendirme öncesi/sonrası ev kontrolü'),
                                                                       ('WEEKLY_VISIT', 'Haftalık Ziyaret', 'Barınağa haftalık rutin ziyaret')
ON CONFLICT (CODE) DO NOTHING;

-- ============================================================
-- DICT_VOLUNTEER_STATUS
-- ============================================================
INSERT INTO PAWCIAL.DICT_VOLUNTEER_STATUS (CODE, LABEL) VALUES
                                                            ('ACTIVE', 'Aktif'),
                                                            ('INACTIVE', 'İnaktif'),
                                                            ('ON_HOLD', 'Ara Vermiş'),
                                                            ('RETIRED', 'Emekli Olmuş')
ON CONFLICT (CODE) DO NOTHING;